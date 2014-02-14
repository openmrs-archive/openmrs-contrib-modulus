package org.openmrs.modulus

import grails.rest.RestfulController
import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

class UserController extends RestfulController {
    static responseFormats = ['json', 'xml']
    UserController() {
        super(User)
    }

    // TODO get JSON parameters to work
    @Transactional
    def login() {
        def user = User.get(params.id)
        session.user = user
        respond user, status: OK
    }

    // TODO write logout method
    def logout() {

    }

}
