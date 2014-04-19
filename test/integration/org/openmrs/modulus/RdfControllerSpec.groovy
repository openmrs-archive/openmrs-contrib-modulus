package org.openmrs.modulus

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import spock.lang.Ignore
import spock.lang.IgnoreRest
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(RdfController)
@Mock([Module, Release, User])
class RdfControllerSpec extends Specification {

    def setup() {
        Module.metaClass.updateSlug = { null }
        new Module(name: 'Testable', legacyId: 'testablemodule', slug: 'testable', releases: [
                new Release(moduleVersion: '1.1', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.1'),
                new Release(moduleVersion: '1.2', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.2'),
                new Release(moduleVersion: '1.3', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.3')
        ], owner: new User(username: 'test')).save()
    }

    void "rdf should render using slug"() {
        setup:
        def rc = new RdfController()

        when:
        rc.params.id = 'testable'
        rc.showModuleRdf()

        then:
        rc.response.status == 200
        rc.modelAndView.viewName == '/rdf/update.rdf'

    }

    void "rdf should render using legacy id"() {
        setup:
        def rc = new RdfController()

        when:
        rc.params.id = 'testablemodule'
        rc.showModuleRdf()

        then:
        rc.response.status == 200
        rc.modelAndView.viewName == '/rdf/update.rdf'
    }

    void "rdf shoudl render using numeric id"() {
        setup:
        def rc = new RdfController()

        when:
        rc.params.id = '1'
        rc.showModuleRdf()

        then:
        rc.response.status == 200
        rc.modelAndView.viewName == '/rdf/update.rdf'
    }
}
