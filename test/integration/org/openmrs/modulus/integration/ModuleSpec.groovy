package org.openmrs.modulus.integration

import org.openmrs.modulus.Module
import org.openmrs.modulus.User
import spock.lang.*

/**
 *
 */
class ModuleSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "should allow searching using maintainer"() {
        given:
        def u = new User(username: "horatio").save(failOnError: true)
        def m = new Module(name: "Test", maintainers: [u]).save(failOnError: true)

        when:
        def results = Module.search("horatio").results

        then:
        results.contains(u)

    }
}
