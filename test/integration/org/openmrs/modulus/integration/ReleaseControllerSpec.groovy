package org.openmrs.modulus.integration

import grails.test.mixin.TestFor
import org.openmrs.modulus.Module
import org.openmrs.modulus.Release
import org.openmrs.modulus.ReleaseController
import org.openmrs.modulus.User
import spock.lang.*

/**
 *
 */
@TestFor(ReleaseController)
class ReleaseControllerSpec extends Specification {

    def springSecurityService

    private givenAuthResources() {
        def user = new User(username: "tester").save(failOnError: true)
        def user2 = new User(username: "imposter").save(failOnError: true)
        def module = new Module(name: "Reporting", maintainers: [user]).save(failOnError: true)
        def release = new Release(module: module).save(failOnError: true, flush: true)
        [user: user, user2: user2, module: module, release: release]
    }

    def setup() {
    }

    def cleanup() {
    }

    @Ignore
    void "should require login to upload to existing release"() {
        given:
//        def rc = new ReleaseController()()

        when:
        params.module
        controller.uploadExisting()

        then:
        response.status == 401
    }

    void "should only allow maintainers to upload to existing release"() {

        given:
        def res = givenAuthResources()
        springSecurityService.metaClass.getCurrentUser = { res.user2 }

        when:
        controller.params.moduleId = res.module.id
        controller.params.id = res.release.id
        controller.uploadExisting()

        then:
        response.status == 403

    }
}
