package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional
import org.apache.commons.io.IOUtils
import org.springframework.web.multipart.MultipartFile

import javax.activation.MimetypesFileTypeMap

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.LENGTH_REQUIRED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

/**
 * Extends Grails' REST controller functionality with methods to handle file uploads over a REST interface.
 * An domain object with a <code>byte[]</code> attribute named <code>rawFile</code> is required. You will also need to
 * set <code>parseRequest: false</code> on any URL mappings to an upload action.
 * @see RestfulController
 * @param < T > a Grails domain object used as the resource of a RestfulUpload instance
 */
// TODO rename to UploadableController
class RestfulUploadController<T> extends RestfulController {

    static allowedMethods = [uploadNewFile: ["POST", "PUT"], uploadToId: ["POST", "PUT"]]

    static UPLOAD_DESTINATION = 'uploads' // within web-app context

    def downloadLinkGeneratorService

    RestfulUploadController(Class<T> resource) {
        super(resource)
    }

    /**
     * Uploads a file and creates a new instance for it.
     * @see #doUpload(java.lang.Class)
     */
    @Transactional
    def uploadNewFile() {
        if (!preUpload())
            return

        def instance = createResource(filename: params.filename)
        doUpload(instance)
    }

    /**
     * Uploads a file to an existing instance. Replaces and uploaded content previously stored at that ID.
     * @see #doUpload(java.lang.Object)
     */
    @Transactional
    def uploadToId() {
        if (!preUpload())
            return

        withInstance { f->
            doUpload(f)
        }
    }

    /**
     * Uploads a file to the repository, from an HTTP POST request.
     *
     * Unlike other API methods, the entire POST body of the request is treated as the file's binary contents. Any
     * other parameters will need to be specified in the URI.
     */
    private def doUpload(T instance) {
        String content = request.getContentType()

        if (content?.contains("multipart/form-data")) {
            handleMultipartUpload(instance)
        } else {
            handleRawUpload(instance)
        }

        if (instance.validate(['rawFile']) && instance.save(flush: true)) {
            respond instance
        } else {
            respond instance.errors, status: UNPROCESSABLE_ENTITY
        }
    }

    private def handleRawUpload(Uploadable instance) {
        def length = new Integer(request.getHeader('Content-Length')),
            input = request.getInputStream()
                
        byte[] buffer = new byte[length]

        // Store bytes into the instance until there are no more to be stored
        int currentByte = 0
        while (input) {
            int chunk = input.read()
            if (chunk == -1) {
                break
            } else {
                buffer[currentByte] = chunk.byteValue()
                currentByte++
            }
        }


        instance.rawFile = buffer
//        instance.path = storeFileInFilesystem(buffer, params.filename)

        def mime = new MimetypesFileTypeMap()

        instance.filename = params.filename
        instance.contentType = mime.getContentType(instance.filename)
    }

    // TODO Support multipart/form-data uploads

    /*private def handleMultipartUpload(Uploadable instance) {
        MultipartFile f = request.getFile('rawFile')

        instance.rawFile = f.bytes
        instance.filename = params.filename || f.name
        instance.contentType = params.contentType || f.contentType
    }*/

//    private def storeFileInFilesystem(byte[] bytes, String name) {
//        def destDir = grailsApplication.parentContext
//                .getResource("$UPLOAD_DESTINATION/$controllerName/${params.id}").getFile()
//        destDir.mkdirs()
//
//        def dest = new File(destDir.getAbsolutePath() + '/' + name)
//        dest.createNewFile()
//
//        // do the transfer
//        def output = dest.newOutputStream()
//        IOUtils.write(bytes, output)
//        output.close()
//
//        dest.getAbsolutePath()
//    }

    /**
     * Downloads the contents of a file to the client.
     * @param id the internal ID of the file
     */
    def download(Integer id) {

        if (!id) {
            respond null, status: BAD_REQUEST
            return
        }

        withInstance id, { instance ->
            if (!params.filename) {

                // redirect to a URL containing the filename
                def path = downloadLinkGeneratorService.URI(controllerName, id, instance.filename)
                redirect uri: path, permanent: true
                return

            } else if (instance.filename != params.filename) {
                respond null, status: NOT_FOUND
                return
            }

            byte[] contents = instance.rawFile
            render file: contents, fileName: instance.filename, contentType: instance.contentType
        }

    }

    /**
     * Look for the instance with the ID specified. If found, call a closure with it available.
     * @param id the internal ID of the resource instance
     * @param c closure that is called if that instance is found
     * @return 404 response, or a closure call with the instance
     */
    private def withInstance(int id, Closure c) {
        T instance = queryForResource(params.id)
        if (instance) {
            c.call instance
        } else {
            respond null, status: NOT_FOUND
        }
    }

    /**
     * Call <code>withInstance</code> using the ID found in <code>params.id</code>.
     */
    private def withInstance(Closure c) {
        def id = Integer.parseInt(params.id)
        withInstance(id, c)
    }

    /**
     * Verifies that the request was not made read-only, and that a Content-Length header was provided.
     * @return false if either requirement failed, true if both pass. Sends an HTTP 4xx response if false.
     */
    private Boolean preUpload() {
        if(handleReadOnly()) {
            respond null, status: CONFLICT
            return false
        }

        if(request.getHeader('Content-Length') == null) {
            respond null, status: LENGTH_REQUIRED
            return false
        }

        true
    }
}
