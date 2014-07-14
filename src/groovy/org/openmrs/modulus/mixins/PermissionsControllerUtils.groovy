package org.openmrs.modulus.mixins

import org.codehaus.groovy.grails.commons.GrailsControllerClass
import org.openmrs.modulus.Module

/**
 * Utilities related to maintainer permissions. Use as a controller mixin to add methods that check
 * permissions of a user operating on a module.
 *
 * Created by Elliott Williams on 7/14/14.
 */
class PermissionsControllerUtils {

    def hasPermissions(Module m) {
        def current = springSecurityService.getCurrentUser()

        def isMaintainer = moduleService.isMaintainer(m, current)
        def isAdmin = current.hasRole("ROLE_ADMIN")

        isMaintainer || isAdmin
    }

    def unauthorized(Module m) {
        if (!hasPermissions(m)) {
            render(status: 403, text: 'You are not authorized to access this resource'  )
            true
        }
        false
    }

}
