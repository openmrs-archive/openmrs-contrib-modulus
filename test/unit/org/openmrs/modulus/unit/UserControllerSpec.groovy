package org.openmrs.modulus.unit

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityUtils
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import org.codehaus.groovy.grails.web.converters.configuration.ConvertersConfigurationInitializer
import org.grails.plugins.marshallers.ExtendedConvertersConfigurationInitializer
import org.grails.plugins.marshallers.JsonMarshallerArtefactHandler
import org.grails.plugins.marshallers.XmlMarshallerArtefactHandler
import org.grails.plugins.marshallers.test.MarshallerUnitSpecMixin
import org.openmrs.modulus.Role
import org.openmrs.modulus.User
import org.openmrs.modulus.UserController
import org.openmrs.modulus.UserRole
import spock.lang.IgnoreRest
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock([User, UserRole, Role])
@TestMixin(MarshallerUnitSpecMixin)
class UserControllerSpec extends Specification {

    def springSecurityService
    def admin

    def setup() {
        User.metaClass.encodePassword = { -> 'bar' }
        User.metaClass.isCurrentUser = { -> false }


        grailsApplication.registerArtefactHandler(new JsonMarshallerArtefactHandler())
        grailsApplication.registerArtefactHandler(new XmlMarshallerArtefactHandler())
        defineBeans {
            convertersConfigurationInitializer(ConvertersConfigurationInitializer)
            extendedConvertersConfigurationInitializer(ExtendedConvertersConfigurationInitializer)
        }

        initialize()

        new User(username: 'bob', password: 'foo').save(failOnError: true)
        new User(username: 'helen', password: 'foo').save(failOnError: true)
        new User(username: 'dash', password: 'foo').save(failOnError: true)
        new User(username: 'violet', password: 'foo').save(failOnError: true)

        admin = new User(username: 'admin', password: 'admin123').save(failOnError: true)
        UserRole.create(admin, Role.findOrCreateByAuthority('ROLE_ADMIN'))
    }

    def cleanup() {
        User.deleteAll()
    }

    void "Test that show() returns the user whose id is specified"() {

        when: "The show() method is called with id=2"
            params.id = 2
            controller.show()

        then: "The object returned should represent the user with id=2, Helen"
            'helen' == response.json.username
    }

    void "show() includes user roles for the currently logged-in user"() {

        when:
            def current = User.findByUsername('bob')
            def role = Role.findOrCreateByAuthority('ROLE_USER')
            UserRole.create(current, role)
            User.metaClass.isCurrentUser = {-> true}
            params.id = current.id
            controller.show()

        then:
            response.json.roles.indexOf('ROLE_USER') > -1
    }

    private def initialize(){
        grailsApplication.mainContext.convertersConfigurationInitializer.initialize(grailsApplication)
        grailsApplication.mainContext.extendedConvertersConfigurationInitializer.initialize()
    }
}
