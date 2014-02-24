package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.HttpHeaders

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND

class ReleaseController extends RestfulUploadController {
    static responseFormats = ['json', 'xml']
    ReleaseController() {
        super(Release)
    }

    /**
     * Send a not found error if a module id is passed that doesn't exist.
     */
    def beforeInterceptor = {
        // Include the parent module
        if (params.moduleId) {
            params.module = [id: params.moduleId]

            if (!Module.exists(params.moduleId)) {
                render text: "Module ID ${params.moduleId} not found", status: NOT_FOUND
                return false
            }
        }
    }


    @Override
    def uploadNew() {
        return super.uploadNew()
    }

    @Override
    def uploadExisting() {
        params.id = params.id ?: params.releaseId

        return super.uploadExisting()
    }

    @Override
    protected Map getParametersToBind() {
        params
    }

    @Override
    protected List listAllResources(Map params) {
        if (params.module) {
            Module.get(params.module.id).releases.toList()
        } else {
            resource.list(params)
        }
    }
}
