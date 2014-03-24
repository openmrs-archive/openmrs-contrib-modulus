package org.openmrs.modulus

import grails.plugin.searchable.SearchableService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import junit.framework.AssertionFailedError
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SearchController)
@Mock([SearchableService])
class SearchControllerSpec extends Specification {

    void "should return a response object"() {
        given:
        def searchMock = mockFor(SearchableService)
        searchMock.demand.search() { q, opts -> [total: 2, offset: 10, results: [
                'firstResult', 'secondResult'
        ]] }
        searchMock.demand.suggestQuery() { q, opts -> 'test suggestion'}
        controller.searchableService = searchMock.createMock()

        when:
        controller.params.q = 'foobar'
        controller.search()
        searchMock.verify()

        then:
        response.json.getInt('totalCount') == 2
        response.json.getInt('offset') == 10
        response.json.getString('suggestion') == 'test suggestion'
        response.json.getJSONArray('items').getString(0) == 'firstResult'
        response.json.getJSONArray('items').getString(1) == 'secondResult'
        notThrown(AssertionFailedError)

    }

    void "should fail a search with no query"() {
        given:
        def searchMock = mockFor(SearchableService)
        controller.searchableService = searchMock.createMock()

        when:
        controller.search()

        then:
        response.status == 422
        response.json.has('errors')
    }

}
