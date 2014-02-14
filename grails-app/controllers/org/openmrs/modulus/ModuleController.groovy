package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.HttpHeaders

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.OK

class ModuleController extends RestfulController {
    def moduleService

    static responseFormats = ['json', 'xml']
    ModuleController() {
        super(Module)
    }

    /**
     * Show a module from numeric ID or slug
     * @return response with Module or 404
     */
    @Override
    def show() {
        if (params.id =~ /[0-9]+/) {
            respond queryForResource(params.id)
        } else {
            respond getBySlug(params.id)
        }

    }


    private def getBySlug(String slug) {
        Module.findWhere(slug: slug)
    }
}
