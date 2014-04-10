package org.openmrs.modulus

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
class UserSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "should hold a username and fullname"() {
        when:
        def u = new User(username: 'horatio', fullname: 'Horatio Hornblower').save()

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

    void "should have a wiki profile link"() {
        when:
        def u = new User(username: 'horatio', fullname: 'Horatio Hornblower').save()

        then:
        u.getWikiProfileURL() == "https://wiki.openmrs.org/display/~horatio"
    }
}
