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
}
