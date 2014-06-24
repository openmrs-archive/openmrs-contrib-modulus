package org.openmrs.modulus

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.IgnoreRest
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(User)
@Mock([UserRole, Role])
class UserSpec extends Specification {

    def setup() {
        User.metaClass.encodePassword = { 'encoded_password' }
    }

    def cleanup() {
    }

    void "should hold a username and fullname"() {
        when:
        def u = new User(username: 'horatio', fullname: 'Horatio Hornblower', password: '123')
                .save(failOnError: true)

        then:
        u.username == 'horatio'
        u.fullname == 'Horatio Hornblower'
    }

    void "should require a username"() {
        when:
        def user = new User()

        then:
        !user.validate()
    }

    void "addDefaultRoles() should add a new user to default roles"() {
        given:
        User.DEFAULT_ROLES = ["TEST_ROLE"]
        def role = new Role(authority: "TEST_ROLE").save(failOnError: true)
        def user = new User(username: 'test', password: '123').save(failOnError: true)

        when:
        user.addDefaultRoles()

        then:
        UserRole.findByUser(user) != null
        UserRole.findByUser(user).role == role
        user.hasRole(role)

    }
}
