package org.openmrs.modulus

import grails.validation.Validateable
import static org.springframework.http.HttpStatus.*

class SearchController {

    static responseFormats = ['json', 'xml']

    def searchService

    def search(SearchCommand cmd) {
        if (cmd.hasErrors()) {
            respond cmd.errors, [status: UNPROCESSABLE_ENTITY]
            return
        }

        respond searchService.search(cmd.q, cmd.properties, cmd.complex)

    }
}

@Validateable
class SearchCommand {
    String q
    Integer max = 50
    Integer offset = 0
    String type = 'module'
    Boolean complex = false
    String sort = 'downloadCount'
    String order = 'desc'
    String defaultProperty = 'name'

    static constraints = {
        q nullable: true
        type inList: ['user',  'module']
    }
}
