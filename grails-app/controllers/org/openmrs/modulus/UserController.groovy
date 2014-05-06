package org.openmrs.modulus

import grails.plugin.springsecurity.annotation.Authorities
import grails.rest.RestfulController
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

class UserController extends RestfulController {

    // inject services
    def springSecurityService
    def moduleService

    static responseFormats = ['json', 'xml']
    UserController() {
        super(User)
    }

    @Override
    @Authorities('admins')
    Object create() {
        return super.create()
    }

    @Override
    @Authorities('admins')
    Object save() {
        return super.save()
    }

    @Override
    Object update() {
        Module instance = moduleService.get(params.id)
        User currentUser = springSecurityService.getCurrentUser()
        if (!moduleService.isMaintainer(instance, currentUser)) {
            render status: FORBIDDEN
        }


        return super.update()
    }

    @Override
    @Authorities('admins')
    Object delete() {
        return super.delete()
    }
}
