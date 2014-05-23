package org.openmrs.modulus

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(["ROLE_USER"])
class CurrentUserController extends RestfulController {

    static responseFormats = ['json', 'xml']
    CurrentUserController() {
        super(User)
    }

    def springSecurityService

    def beforeInterceptor = {

        params.id = springSecurityService.isLoggedIn() ?
                springSecurityService.getCurrentUser().id :
                null
    }




}
