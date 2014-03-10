package org.openmrs.modulus

import grails.plugins.SlugGeneratorService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.aspectj.lang.annotation.Before
import spock.lang.Shared
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(RdfController)
@Mock([Module, Release])
class RdfControllerSpec extends Specification {

    def setup() {
        Module.metaClass.updateSlug = { null }
    }

    void "rdf generation"() {
        setup:
        def module = new Module(name: 'Testable', legacyId: 'testablemodule', slug: 'testable', releases: [
                new Release(moduleVersion: '1.1', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.1'),
                new Release(moduleVersion: '1.2', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.2'),
                new Release(moduleVersion: '1.3', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.3')
        ]).save()

        when:
        params.id = 'testable'
        controller.showModuleRdf()

        then:
        response.status == 200

    }

    void "rdf generation using legacy id"() {
        setup:
        def module = new Module(name: 'Testable', legacyId: 'testablemodule', slug: 'testable', releases: [
                new Release(moduleVersion: '1.1', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.1'),
                new Release(moduleVersion: '1.2', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.2'),
                new Release(moduleVersion: '1.3', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.3')
        ]).save()

        when:
        params.id = 'testablemodule'
        controller.showModuleRdf()

        then:
        response.status == 200
    }

    void "rdf generation using numeric id"() {
        setup:
        def module = new Module(name: 'Testable', legacyId: 'testablemodule', slug: 'testable', releases: [
                new Release(moduleVersion: '1.1', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.1'),
                new Release(moduleVersion: '1.2', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.2'),
                new Release(moduleVersion: '1.3', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.3')
        ]).save()

        when:
        params.id = module.id.toString()
        controller.showModuleRdf()

        then:
        response.status == 200
    }
}
