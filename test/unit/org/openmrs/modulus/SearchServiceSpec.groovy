package org.openmrs.modulus

import grails.plugin.searchable.SearchableService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import junit.framework.AssertionFailedError
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SearchService)
@Mock(SearchableService)
class SearchServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }


    void "should return a valid search result"() {
        given:
        def searchMock = mockFor(SearchableService)
        searchMock.demand.search() { q, opts -> [total: 2, offset: 10, results: [
                'firstResult', 'secondResult'
        ]] }
        searchMock.demand.suggestQuery() { q, opts -> 'test suggestion'}
        service.searchableService = searchMock.createMock()

        when:
        def result = service.search('foobar')
        searchMock.verify()

        then:
        result.totalCount == 2
        result.offset == 10
        result.suggestion == 'test suggestion'
        result.items[0] == 'firstResult'
        result.items[1] == 'secondResult'
        notThrown(AssertionFailedError)

    }

    void "should return an empty search result when nothing exists"() {
        given:
        def searchMock = mockFor(SearchableService)
        searchMock.demand.search() { q, opts -> [total: 0, offset: 0, results: []] }
        searchMock.demand.suggestQuery() { q, opts -> null }
        service.searchableService = searchMock.createMock()


        when:
        def result = service.search('nothing')

        then:
        result == [totalCount: 0, offset: 0, items: [], suggestion: null]
    }

    void "should always no results when no query is passed"() {
        given:
        def searchMock = mockFor(SearchableService)
        searchMock.demand.search() { q, opts -> [total: 1, offset: 0, results: ["firstResult"]] }
        searchMock.demand.suggestQuery() { q, opts -> null }
        service.searchableService = searchMock.createMock()

        when:
        def result = service.search(null)

        then:
        result == [totalCount: 0, offset: 0, items: [], suggestion: null]
    }
}
