package org.openmrs.modulus

import grails.plugins.SlugGeneratorService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import junit.framework.AssertionFailedError
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Module)
@Mock([SlugGeneratorService, Release])
class ModuleSpec extends Specification {


    def setup() {
    }

    def cleanup() {
    }

    void "releases should be a list of releases sorted by descending version number"() {
        given:
        Module module = new Module(releases: [
                new Release(moduleVersion: '1.0.1'),
                new Release(moduleVersion: '1.4.6'),
                new Release(moduleVersion: '1.4.10'),
                new Release(moduleVersion: '1.1.2.4')
        ]).save()

        when:
        def releases = module.releases

        then:
        releases.collect { it.moduleVersion } == ['1.4.10', '1.4.6', '1.1.2.4', '1.0.1']
    }
}
