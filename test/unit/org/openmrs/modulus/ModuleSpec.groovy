package org.openmrs.modulus

import grails.plugins.SlugGeneratorService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
@TestFor(Module)
@Mock([SlugGeneratorService, Release, Screenshot, User])
class ModuleSpec extends Specification {


    def setup() {
    }

    def cleanup() {
    }

    void "releases should be a list of releases sorted by descending version number"() {
        when:
        def m = new Module(releases: [
                new Release(moduleVersion: '1.0.1'),
                new Release(moduleVersion: '1.4.6'),
                new Release(moduleVersion: '1.4.10'),
                new Release(moduleVersion: '1.1.2.4')
        ])
        m.save()

        then:
        m.validate() == true
        m.releases.collect { it.moduleVersion } == ['1.4.10', '1.4.6', '1.1.2.4', '1.0.1']
    }

    void "should have a single owner"() {
        when:
        def m = new Module(owner: 'horatio').save()

        then:
        m.owner == 'horatio'
    }

    void "should allow multiple maintainers"() {
        given:
        def u = [
                new User(username: 'horatio').save(),
                new User(username: 'polonius').save(),
                new User(username: 'ophelia').save()
        ]

        when:
        def m = new Module(owner: u[0].toString(), maintainers: u).save()

        then:
        m.maintainers == u.toSet()
    }
}
