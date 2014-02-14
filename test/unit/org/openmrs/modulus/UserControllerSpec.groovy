package org.openmrs.modulus

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@Mock(User)
class UserControllerSpec extends Specification {

    def setup() {
        new User(username: 'bob', fullname: 'Bob Parr').save()
        new User(username: 'helen', fullname: 'Helen Parr').save()
        new User(username: 'dash', fullname: 'Dashell Parr').save()
        new User(username: 'violet', fullname: 'Violet Parr').save()
    }

    def cleanup() {
        User.deleteAll()
    }

    void "Test that index() returns a list of users"() {

        when: "The index method is called"
            controller.index()

        then: "A list of all users is returned"
            4 == response.json.length()
            'bob' == response.json.get(0).username
    }

    void "Test that show() returns the user whose id is specified"() {

        when: "The show() method is called with id=2"
            params.id = 2
            controller.show()

        then: "The object returned should represent the user with id=2, Helen"
            'helen' == response.json.username
    }
}
