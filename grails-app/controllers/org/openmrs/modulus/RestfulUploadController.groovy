package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils

import javax.activation.MimetypesFileTypeMap
import java.nio.file.Path
import java.nio.file.Paths

import static org.springframework.http.HttpStatus.BAD_REQUEST
import static org.springframework.http.HttpStatus.CONFLICT
import static org.springframework.http.HttpStatus.LENGTH_REQUIRED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

/**
 * Extends Grails' REST controller functionality with methods to handle file uploads over a REST interface.
 * Controls actions to Uploadable domain objects
 * set <code>parseRequest: false</code> on any URL mappings to an upload action.
 * @see Uploadable
 * @see RestfulController
 * @param < T > a Grails domain object inheriting Uploadable
 */
// TODO rename to UploadableController
class RestfulUploadController<T> extends RestfulController {

    static allowedMethods = [uploadNewFile: ["POST"], uploadExisting: ["PUT"]]

    static UPLOAD_DESTINATION = 'modulus_uploads' // within web-app context

    def downloadLinkGeneratorService
    def uploadableService

    RestfulUploadController(Class<T> resource) {
        super(resource)
    }

    /**
     * Uploads a file and creates a new instance for it.
     */
    @Transactional
    def uploadNew() {
        if (!preUpload())
            return

        def instance = createResource(params)
        doUpload(instance)

        if (instance.validate(['rawFile', 'filename', 'contentType']) && instance.save(flush: true)) {
            respond instance
        } else {
            respond instance.errors, status: UNPROCESSABLE_ENTITY
        }
    }

    /**
     * Uploads a file to an existing instance. Replaces and uploaded content previously stored at that ID.
     */
    @Transactional
    def uploadExisting() {
        if (!preUpload())
            return

        withInstance { instance ->
            doUpload(instance)

            if (instance.validate(['rawFile', 'filename', 'contentType']) && instance.save(flush: true)) {
                respond instance
            } else {
                respond instance.errors, status: UNPROCESSABLE_ENTITY
            }
        }
    }

    /**
     * Uploads a file to the repository, from an HTTP POST request.
     *
     * Unlike other API methods, the entire POST body of the request is treated as the file's binary contents. Any
     * other parameters will need to be specified in the URI.
     */
    protected def doUpload(T instance) {
        String content = request.getContentType()

        if (content?.contains("multipart/form-data")) {
            handleMultipartUpload(instance)
        } else {
            handleRawUpload(instance)
        }
    }

    protected def handleRawUpload(Uploadable instance) {

        if (!params.filename) {
            return render(
                    text: '"filename" paramater required for binary file uploads',
                    status: UNPROCESSABLE_ENTITY
            )
        }

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


//        instance.rawFile = buffer
        instance.path = uploadableService.uploadFile(instance, buffer, params.filename)
    }

    // TODO Support multipart/form-data uploads

    /*private def handleMultipartUpload(Uploadable instance) {
        MultipartFile instance = request.getFile('rawFile')

        instance.rawFile = instance.bytes
        instance.filename = params.filename || instance.name
        instance.contentType = params.contentType || instance.contentType
    }*/

    /**
     * Downloads the contents of a file to the client.
     * @param id the internal ID of the file
     */
    def download(Integer id) {

        if (!id) {
            respond null, status: BAD_REQUEST
            return
        }

        withInstance id, { Uploadable instance ->
            if (!params.filename) {

                // redirect to a URL containing the filename
                def path = downloadLinkGeneratorService.URI(controllerName, id, instance.filename)
                redirect uri: path, permanent: true
                return

            } else if (instance.filename != params.filename) {
                respond null, status: NOT_FOUND
                return
            }

            Path path = Paths.get(instance.path)
            File file = path.toFile()
            byte[] contents = FileUtils.readFileToByteArray(file)


            render file: contents, fileName: instance.filename, contentType: instance.contentType
        }

    }

    /**
     * Look for the instance with the ID specified. If found, call a closure with it available.
     * @param id the internal ID of the resource instance
     * @param c closure that is called if that instance is found
     * @return 404 response, or a closure call with the instance
     */
    protected def withInstance(int id, Closure c) {
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
    protected def withInstance(Closure c) {
        def id = Integer.parseInt(params.id)
        withInstance(id, c)
    }

    /**
     * Verifies that the request was not made read-only, and that a Content-Length header was provided.
     * @return false if either requirement failed, true if both pass. Sends an HTTP 4xx response if false.
     */
    protected Boolean preUpload() {
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
