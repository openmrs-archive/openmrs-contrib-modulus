package org.openmrs.modulus.unit

import grails.plugin.springsecurity.SpringSecurityService
import grails.rest.RestfulController
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.openmrs.modulus.Module
import org.openmrs.modulus.ModuleController
import org.openmrs.modulus.Role
import org.openmrs.modulus.User
import org.openmrs.modulus.UserRole
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ModuleController)
@Mock([Module, User, UserRole, Role, SpringSecurityService])
class ModuleControllerSpec extends Specification {

    def setup() {
        User.metaClass.encodePassword = { 'encoded_password '}
    }

    def cleanup() {
    }

    void "creating a module should add the current user as owner and maintainer in params"() {
        given:
        def testUser = new User(username: 'test', password: 'foo').save(failOnError: true)
        UserRole.create(testUser, Role.findOrSaveByAuthority('ROLE_USER'))
        RestfulController.metaClass.save = {}

        def securityMock = mockFor(SpringSecurityService)
        securityMock.demand.getCurrentUser() { -> testUser }
        controller.springSecurityService = securityMock.createMock()

        when:
        controller.save()

        then:
        response.json.owner == testUser.username
        response.json.maintainers[0].id == testUser.id
    }
}
