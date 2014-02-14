package org.openmrs.modulus

/**
 * Works in tandem with the "/api" routing to make the controllers more REST-friendly.
 */
class ApiResourceFilters {

    def filters = {

        // If the API is being browsed by a non-API client, send it a JSON response anyway. By default, grails
        // will respond with the HTML UI, but when "/api" is part of the request URI the client should
        // definitely get the API.
        someURIs(uri: '/api/**') {
            before = {
                request.withFormat {
                    json { return true }
                    xml  { return true }
                    '*'  { params.format = "json" }
                }
            }
        }
    }
}
