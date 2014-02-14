package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional

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
class RestfulUploadController<T> extends RestfulController {

    static allowedMethods = [uploadNewFile: ["POST", "PUT"], uploadToId: ["POST", "PUT"]]

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
        instance.rawFile = request.getReader().text.getBytes()
        instance.filename = params.filename

        if (instance.validate(['rawFile']) && instance.save(flush: true)) {
            respond instance
        } else {
            respond instance.errors, status: UNPROCESSABLE_ENTITY
        }

    }

    /**
     * Downloads the contents of a file to the client.
     * @param id the internal ID of the file
     */
    def download(Integer id) {
        log.debug("download() called, id=$id")
        withInstance id, { instance ->
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
