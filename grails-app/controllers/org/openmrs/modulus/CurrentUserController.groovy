package org.openmrs.modulus

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

@Secured(["ROLE_USER"])
class CurrentUserController extends UserController {

    static responseFormats = ['json']

    def springSecurityService

    def beforeInterceptor = {

        params.id = springSecurityService.isLoggedIn() ?
                springSecurityService.getCurrentUser().id :
                null
    }




}
