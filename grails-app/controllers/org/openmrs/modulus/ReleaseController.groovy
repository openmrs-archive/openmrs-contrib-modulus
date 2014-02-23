package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.HttpHeaders

import static org.springframework.http.HttpStatus.CREATED

class ReleaseController extends RestfulUploadController {
    static responseFormats = ['json', 'xml']
    ReleaseController() {
        super(Release)
    }

    @Override
    def uploadNew() {
        log.debug("uploadNew params=${params}")
        return super.uploadNew()
    }

    @Override
    def uploadExisting() {
        params.id = params.id ?: params.releaseId

        return super.uploadExisting()
    }

    @Override
    protected Object createResource(Map params) {

        // Include the parent module
        if (params.moduleId) {
            params.module = [id: params.moduleId]
        }

        params.foo = 'bar'
        request.parameterMap['baz'] = 'qux'

        return Release.newInstance(params)
    }
}
