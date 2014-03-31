package org.openmrs.modulus

import grails.transaction.Transactional

@Transactional
class SearchService {

    def searchableService

    /**
     * Search for Modules using the grails-searchable luncene index
     * @param query string to search for
     * @param params results-adjusting parameters like max and offset
     * @return a Map of result data
     */
    def search(String query, Map params) {

        if (!query) {
            return [
                    totalCount: 0,
                    offset: 0,
                    items: [],
                    suggestion: null
            ]
        }

        // Create a version of q with wildcard after each word that
        // is not "and", "or", or within double quotes, for example:
        // Lorem or ipsum "dolor sit" → Lorem* or ipsum* "dolor sit"
        // This allows for more natural searches and leaves room for
        // supporting quotes & conjunctions in future queries.
        def qWildcards = query.replaceAll(
                /(?!and\b|or\b)(\b[^\s]+)\b(?=([^"]*"[^"]*")*[^"]*$)/
        ){ all, word, dummy -> "$word*" }

        def search = searchableService.search(qWildcards, params),
            suggest = searchableService.suggestQuery(query, [
                    allowSame: false,
                    escape: true
            ])

        return [
                totalCount: search.total,
                offset: search.offset,
                items: search.results,
                suggestion: suggest
        ]

    }

    /**
     * Search for Modules using the grails-searchable luncene index
     * @param query string to search for
     * @return a Map of result data
     */
    def search(String query) {
        search(query, [:])
    }
}