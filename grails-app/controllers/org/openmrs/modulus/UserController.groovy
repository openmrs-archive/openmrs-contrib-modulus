package org.openmrs.modulus

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

import static org.springframework.http.HttpStatus.*

@Secured("ROLE_ADMIN")
class UserController extends RestfulController {

    // inject services
    def springSecurityService
    def moduleService

    static responseFormats = ['json', 'xml']
    UserController() {
        super(User)
    }

    @Override
    @Secured("ROLE_USER")
    Object update() {
        User instance = queryForResource(params.id)
        User currentUser = springSecurityService.getCurrentUser()

        log.debug("checking if $currentUser can edit $instance")
        if (instance != currentUser) {
            return render(status: FORBIDDEN)
        }


        return super.update()
    }

    @Override
    @Secured("permitAll")
    Object show() {
        return super.show()
    }
}
