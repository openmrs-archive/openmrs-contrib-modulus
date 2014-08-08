package org.openmrs.modulus

import grails.converters.JSON
import grails.transaction.Transactional
import org.apache.lucene.queryParser.ParseException

@Transactional
class SearchService {

    def searchableService
    def grailsApplication

    /**
     * Search for Modules using the grails-searchable luncene index
     * @param query string to search for
     * @param params results-adjusting parameters like max and offset
     * @param complex whether to parse the query as a lucene expression or not
     * @return a Map of result data
     */
    def search(String query, Map params, Boolean complex = false) {


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

        // Determine whether this is a complex query or not.
        def searchFor = complex ? query : qWildcards

        // Get the proper search class
        def searcher = getSearcher(params.type)

        def search = searcher.search(searchFor, params)



        def suggest = searcher.suggestQuery(query, [
                    allowSame: false,
                    escape: true
            ])

        // Remove the `+(alias:x)` component of the suggestion. For
        // example:
        // +address +(alias:module)    =>    address
        if (suggest) {
            def m = suggest =~ /\+(.+) \+\(alias:\w+\)/
            if (m) {
                suggest = m[0][1]
            }

            if (suggest == query) {
                suggest = null
            }
        }


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

    /**
     * Limit search to a specific class, if a "type" parameter is passed
     * @param type what type of object to search for (e.g. "module" or "user")
     * @return class implementing searchable methods
     */
    def getSearcher(String type) {

        def searcher
        switch (type) {
            case "module":
                searcher = Module
                break
            case "user":
                searcher = User
                break
            case "tag":
                searcher = Tag
                JSON.use('tagShow') // Set the JSON configuration for the rest of the request to marshall full tags
                break
            default:
                searcher = searchableService // will search *all* classes
        }

        return searcher
    }
}