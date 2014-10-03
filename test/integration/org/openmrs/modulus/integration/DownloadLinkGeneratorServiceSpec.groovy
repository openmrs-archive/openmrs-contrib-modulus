package org.openmrs.modulus.integration

import grails.test.mixin.TestFor
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.mapping.LinkGenerator
import org.openmrs.modulus.DownloadLinkGeneratorService
import spock.lang.*

/**
 *
 */
@TestFor(DownloadLinkGeneratorService)
class DownloadLinkGeneratorServiceSpec extends Specification {

    LinkGenerator grailsLinkGenerator

    def setup() {
    }

    def cleanup() {
    }

    void "URL should call GrailsLinkGenerator"() {
        setup:
        LinkGenerator linkGeneratorMock = Mock()
        service.grailsLinkGenerator = linkGeneratorMock

        when:
        service.URL("release", 1, "module.omod")

        then:
        1 * linkGeneratorMock.link( { it.absolute == true } )
    }

    void "URL should return an absolute URL"() {
        setup:
        service.grailsLinkGenerator = grailsLinkGenerator
        def serverURL = grailsApplication.config.grails.serverURL

        when:
        def url = service.URL("release", 1, "module.omod")

        then:
        url == "$serverURL/api/releases/1/download/module.omod"
    }
}
