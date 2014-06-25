package org.openmrs.modulus

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(LoginController)
class LoginControllerSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "internalOauthUrl should work with a serverURL containing a path"() {
        given:
        grailsApplication.config.grails.serverURL = "https://example.com/modulus"
        def transaction = [properties: [foo: "bar"]]

        when:
        def url = controller.internalOauthUrl(transaction)

        then:
        url == "https://example.com/modulus/oauth/authorize?foo=bar"

    }
}
