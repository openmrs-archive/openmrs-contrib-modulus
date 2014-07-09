package org.openmrs.modulus.unit

import grails.plugin.searchable.SearchableService
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import junit.framework.AssertionFailedError
import org.openmrs.modulus.Module
import org.openmrs.modulus.SearchService
import org.openmrs.modulus.User
import spock.lang.IgnoreRest
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(SearchService)
@Mock([SearchableService, Module, User])
class SearchServiceSpec extends Specification {

    def setup() {
        Module.metaClass.updateSlug = { null }
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

    void "getSearcher should return the Module class when module type is passed"() {
        when:
        def searcher = service.getSearcher('module')

        then:
        searcher == Module
    }

    void "getSearcher should return the User class when user type is passed"() {
        when:
        def searcher = service.getSearcher('user')

        then:
        searcher == User
    }

    void "getSearcher should return the searchableService by default"() {
        when:
        def searcher = service.getSearcher()

        then:
        searcher instanceof SearchableService
    }

    void "search should support complex lucene queries"() {
        given:
        new Module(name: 'foo', description: 'reporting').save(failOnError: true)
        new Module(name: 'reporting', description: 'bar').save(failOnError: true)
        def searchMock = mockFor(SearchableService)

        def response = [total: 0, offset: 0, results: []]
        def firstSearch = ''
        searchMock.demand.search() { q, o ->
            firstSearch = q
            response
        }
        searchMock.demand.suggestQuery() { _ -> null }

        service.searchableService = searchMock.createMock()

        when:
        service.search('name:"reporting"', [:], true)

        then:
        firstSearch == 'name:"reporting"'


    }
}
