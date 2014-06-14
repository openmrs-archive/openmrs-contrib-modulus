package org.openmrs.modulus

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(["ROLE_USER"])
class CurrentUserController {

    static responseFormats = ['json']

    def springSecurityService

    def index = {
        forward controller: "user", action: "show", params:
                [id: springSecurityService.getCurrentUser().id]
    }




}
