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

        respond searchService.search(cmd.q, cmd.properties)

    }
}

@Validateable
class SearchCommand {
    String q
    Integer max
    Integer offset

    static constraints = {
        q blank: false
    }
}