package org.openmrs.modulus

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import groovy.mock.interceptor.StubFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import spock.lang.Ignore
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DownloadLinkGeneratorService)
class DownloadLinkGeneratorServiceSpec extends Specification {

    String serverURL = "http://example.com"
    
    Long id = 1
    String filename = "foobar.omod"


    def setup() {
        def linkGenerator = mockFor(LinkGenerator)
        linkGenerator.demand.link() { _ -> "/api/releases/${id}/download/${filename}".toString() }



        service.grailsLinkGenerator = linkGenerator.createMock()
        service.grailsApplication.config.grails.serverURL = serverURL
    }

    def cleanup() {
    }

    void "URI should return a relative URI to the download endpoint"() {
        expect:
        service.URI("release", id, filename) ==
                "/api/releases/${id}/download/${filename}"
    }

    void "URL should return an absolute URL to the download endpoint"() {
        expect:
        service.URL("release", id, filename) ==
                "http://example.com/api/releases/${id}/download/${filename}"
    }
}
