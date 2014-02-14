package org.openmrs.modulus

import grails.plugins.SlugGeneratorService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.openmrs.modulus.Module
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Module)
@Mock([SlugGeneratorService])
class ModuleSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "slug set on creation"() {
        when:
            def module = Module.newInstance(name: 'Foobar Module', description: 'Check this out',
                    documentationURL: 'http://example.com').save()
            module.save()

        then:
            module.slug == 'foobar-module'
    }

    void "slug modified on update"() {
        when:
            def module = Module.newInstance(name: 'Foobar Module', description: 'Check this out',
                documentationURL: 'http://example.com').save()
            module.save()
            module.name = 'Totally different module'
            module.save()

        then:
            module.slug == 'totally-different-module'
    }
}
