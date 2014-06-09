package org.openmrs.modulus

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

import static org.springframework.http.HttpStatus.*

@Secured("ROLE_ADMIN")
class UserController extends RestfulController {

    // inject services
    def springSecurityService
    def moduleService

    static responseFormats = ['json']
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
        User user = queryForResource(params.id)
        if (!user) {
            return respond(status: 404, text: "User not found")
        }
        if (user.isCurrentUser()) {
            JSON.use('showRoles') { render user as JSON }
        } else {
            render user as JSON
        }
    }
}
