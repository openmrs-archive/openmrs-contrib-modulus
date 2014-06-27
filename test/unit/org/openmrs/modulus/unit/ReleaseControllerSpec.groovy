package org.openmrs.modulus.unit

import grails.plugins.SlugGeneratorService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import groovy.mock.interceptor.StubFor
import org.openmrs.modulus.Module
import org.openmrs.modulus.OmodParserService
import org.openmrs.modulus.Release
import org.openmrs.modulus.ReleaseController
import org.openmrs.modulus.RestfulUploadController
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ReleaseController)
@Mock([Module, Release])
class ReleaseControllerSpec extends Specification {

    def setup() {
        Module.metaClass.updateSlug = { null }

        new Module(id: 1, name: 'Foo').save()
        new Module(id: 2, name: 'Bar').save()
        new Release(module: Module.get(1), moduleVersion:'1.0').save()
        new Release(module: Module.get(1), moduleVersion:'1.1').save()
        new Release(module: Module.get(1), moduleVersion:'1.2').save()
        new Release(module: Module.get(2), moduleVersion:'1.0').save()
    }

    def cleanup() {
    }

    void "listAllResources should limit # of results when max parameter is passed"() {
        given:
        def opts = [module: [id: 1], max: 1]

        when:
        def results = controller.listAllResources(opts)

        then:
        results.size() == 1
    }

    void "listAllResources should only return releases for the module specified"() {
        given:
        def opts = [module: [id: 2]]

        when:
        def results = controller.listAllResources(opts)

        then:
        results.size == 1
    }

    void "listAllResources should order by descending version number"() {
        given:
        def opts = [module: [id: 1], sort: 'moduleVersion', order: 'desc']

        when:
        def results = controller.listAllResources(opts)

        then:
        results.collect { r -> r.moduleVersion } == ['1.2', '1.1', '1.0']
    }

    void "listAllResources should offset # of results with offset parameter"() {
        given:
        def opts = [module: [id: 1], sort: 'moduleVersion', order: 'desc', offset: 1]

        when:
        def results = controller.listAllResources(opts)

        then:
        results.collect { r -> r.moduleVersion } == ['1.1', '1.0']
    }

    void "doUpload should parse and add metadata"() {
        given:
        RestfulUploadController.metaClass.doUpload = { }
        def parserMock = mockFor(OmodParserService)
        parserMock.demand.getMetadata() { _ ->
            [
                id         : 'webservices.rest',
                name       : 'Rest Web Services',
                version    : '2.5.e52eb0',
                require_version: '1.8.1',
                description: 'Publishes Rest Web Services exposing the OpenMRS API'
            ]
        }
        controller.omodParserService = parserMock.createMock()

        def module = new Module(name: "Test Module").save(failOnError: true)
        def release = new Release(module: module).save(failOnError: true)

        release.path = '/doesnt/exist.omod'

        when:
        controller.doUpload(release)

        then:
        module.legacyId == 'webservices.rest'
        module.name == 'Test Module' // should *not* have been overwritten
        module.description == 'Publishes Rest Web Services exposing the OpenMRS API'
        release.moduleVersion == '2.5.e52eb0'
        release.requiredOMRSVersion == '1.8.1'
    }
}
