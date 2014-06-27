package org.openmrs.modulus.unit

import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import org.openmrs.modulus.NonOmodException
import org.openmrs.modulus.TestUtils
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
class OmodParserServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "getMetadata() should return properties of a valid omod"() {
        given:
        File omod = TestUtils.download('https://modules.openmrs.org/modulus/api/releases/1029/download/webservices.rest-2.5.e52eb0.omod')

        when:
        def data = service.getMetadata(omod)

        then:
        data.id == 'webservices.rest'
        data.require_version == '1.8.1'
        data.version == '2.5.e52eb0'
        data.name == 'Rest Web Services'
        data.description == 'Publishes Rest Web Services exposing the OpenMRS API'

    }

    void "ensureOmodFile() should throw a NonOmodError if file is not an omod"() {
        given:
        File nonOmod = TestUtils.download('http://upload.wikimedia.org/wikipedia/commons/a/af/Fred_Rogers.jpg')

        when:
        service.ensureOmodFile(nonOmod)

        then:
        thrown(NonOmodException)
    }

}
