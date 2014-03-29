package org.openmrs.modulus

import grails.validation.Validateable
import static org.springframework.http.HttpStatus.*

class SearchController {

    static responseFormats = ['json', 'xml']

    def searchableService

    def search(SearchCommand cmd) {
        if (cmd.hasErrors()) {
            respond cmd.errors, [status: UNPROCESSABLE_ENTITY]
            return
        }

        // Create a version of q with wildcard after each word that
        // is not "and", "or", or within double quotes, for example:
        // Lorem or ipsum "dolor sit" → Lorem* or ipsum* "dolor sit"
        // This allows for more natural searches and leaves room for
        // supporting quotes & conjunctions in future queries.
        def qWildcards = cmd.q.replaceAll(
            /(?!and\b|or\b)(\b[^\s]+)\b(?=([^"]*"[^"]*")*[^"]*$)/
            ){ all, word, dummy -> "$word*" }

        def search = searchableService.search(qWildcards, cmd),
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