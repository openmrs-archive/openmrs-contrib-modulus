package org.openmrs.modulus.views

import grails.test.mixin.Mock
import grails.test.mixin.TestMixin
import grails.test.mixin.domain.DomainClassUnitTestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import grails.test.mixin.web.GroovyPageUnitTestMixin
import org.custommonkey.xmlunit.XMLUnit
import org.openmrs.modulus.Module
import org.openmrs.modulus.Release
import org.openmrs.modulus.User
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin([GroovyPageUnitTestMixin, DomainClassUnitTestMixin])
@Mock([Module, Release, User])
class UpdateRdfViewSpec extends Specification {

    def setup() {
        mockDomain(Module, [[
            name: 'Testable', legacyId: 'testablemodule', slug: 'testable', releases: [
                    new Release(moduleVersion: '1.1', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.1'),
                    new Release(moduleVersion: '1.2', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.2'),
                    new Release(moduleVersion: '1.3', requiredOMRSVersion: '1.0', downloadURL: 'http://example.com/v1.3')
            ], owner: new User(username: 'test')
        ]])
        Module.metaClass.updateSlug = { null }
    }

    def setupSpec() {
        XMLUnit.setIgnoreWhitespace(true)
        XMLUnit.setIgnoreComments(true)
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true)
        XMLUnit.setNormalizeWhitespace(true)
    }

    def control = """
<updates configVersion="1.1" moduleId="testablemodule">

    <update>
        <currentVersion>1.3</currentVersion>
        <requireOpenMRSVersion>1.0</requireOpenMRSVersion>
        <downloadURL>http://example.com/v1.3</downloadURL>
    </update>

    <update>
        <currentVersion>1.2</currentVersion>
        <requireOpenMRSVersion>1.0</requireOpenMRSVersion>
        <downloadURL>http://example.com/v1.2</downloadURL>
    </update>

    <update>
        <currentVersion>1.1</currentVersion>
        <requireOpenMRSVersion>1.0</requireOpenMRSVersion>
        <downloadURL>http://example.com/v1.1</downloadURL>
    </update>

</updates>"""

    void "should render expected rdf file"() {
        when:
        def view = render(view: '/rdf/update.rdf', model: [module: Module.get(1)])

        then:
        XMLUnit.compareXML(control, view).identical()
    }
}
