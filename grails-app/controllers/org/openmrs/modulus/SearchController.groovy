package org.openmrs.modulus

import grails.validation.Validateable
import static org.springframework.http.HttpStatus.*

class SearchController {

    def searchableService

    def search(SearchCommand cmd) {
        if (cmd.hasErrors()) {
            respond cmd.errors, [status: UNPROCESSABLE_ENTITY]
            return
        }

        def search = searchableService.search(cmd.q, cmd),
            suggest = searchableService.suggestQuery(cmd.q, [
                    allowSame: false,
                    escape: true
            ])

        respond([
                totalCount: search.total,
                offset: search.offset,
                items: search.results,
                suggestion: suggest
        ])

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