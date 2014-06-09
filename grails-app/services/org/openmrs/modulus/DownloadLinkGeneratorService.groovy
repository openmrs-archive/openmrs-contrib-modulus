package org.openmrs.modulus

import grails.transaction.Transactional
import org.apache.commons.io.FilenameUtils
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

@Transactional
class DownloadLinkGeneratorService {

    LinkGenerator grailsLinkGenerator
    GrailsApplication grailsApplication

    def URL(String controller, long id, String filename) {

        return grailsLinkGenerator.link(
                controller: controller,
                action: 'download',
                id: id,
                params: [filename: filename],
                mapping: 'downloadResource',
        )
    }

    def URI(String controller, long id, String filename) {
        return URL(controller, id, filename).replace(
                grailsLinkGenerator.getServerBaseURL(), '')
    }
}
