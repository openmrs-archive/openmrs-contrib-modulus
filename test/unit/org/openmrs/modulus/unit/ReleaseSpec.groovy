package org.openmrs.modulus.unit

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import grails.test.mixin.domain.DomainClassUnitTestMixin
import org.openmrs.modulus.DownloadLinkGeneratorService
import org.openmrs.modulus.Module
import org.openmrs.modulus.Release
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Release)
@Mock(Module)
class ReleaseSpec extends Specification {

    DownloadLinkGeneratorService downloadLinkGeneratorServiceMock = Mock()

    def setup() {
        config.grails.serverURL = "http://modulus-test.org"
        Release.downloadLinkGeneratorService = downloadLinkGeneratorServiceMock

        downloadLinkGeneratorServiceMock.URL(_, _, _) >> "http://modulus-test.org/api/releases/1/download/modulus-test.omod"
    }

    def cleanup() {
    }

    void "generateDownloadURL should generate a download url"() {
        when:
        Module m = new Module()
        Release r = new Release(moduleVersion: 1.0, requiredOMRSVersion: 1.0, filename: 'modulus-test.omod')
        m.addToReleases(r)
        m.save(failOnError: true)
        r.generateDownloadURL()

        then:
        1 * downloadLinkGeneratorServiceMock.URL('release', 1, 'modulus-test.omod') >> "http://modulus-test.org/api/releases/1/download/modulus-test.omod"
        r.downloadURL == "http://modulus-test.org/api/releases/1/download/modulus-test.omod"
    }


    void "creating a new release should generate a download url"() {
        when:
        Module m = new Module()
        Release r = new Release(moduleVersion: 1.0, requiredOMRSVersion: 1.0, filename: 'modulus-test.omod')
        m.addToReleases(r)
        m.save(failOnError: true)
        r.afterInsert()

        then:
        r.downloadURL == "http://modulus-test.org/api/releases/1/download/modulus-test.omod"
    }
}
