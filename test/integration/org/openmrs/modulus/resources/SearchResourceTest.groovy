package org.openmrs.modulus.resources

import com.google.gson.Gson
import org.grails.jaxrs.itest.IntegrationTestCase
import org.junit.Before
import org.junit.Test
import org.openmrs.modulus.Module
import static org.junit.Assert.*

/**
 * Created by herooftime on 3/21/14.
 */
class SearchResourceTest extends IntegrationTestCase {


    @Before
    void setupDatabase() {
        new Module([name: "EMR API"]).save()
        new Module([name: "XForms"]).save()
        new Module([name: "Kenya EMR"]).save()
    }

    @Test
    void shouldReturnResultsForQuery() {
        def headers = ['Content-Type': 'application/json', 'Accept': 'application/json']
        def expectNames = ["EMR API", "Kenya EMR"] as Set
        def gson = new Gson();

        sendRequest('/api/search?q=emr', 'GET', headers)

        def json = gson.fromJson(response.contentAsString, Map)

        assertEquals(200, response.status)

        assertEquals(2, json.totalCount, 0)

        for (def m : json.items) {
            assertTrue(expectNames.contains(m.name))
        }
    }

    @Test
    void shouldSuggestOnTypo() {
        def headers = ['Content-Type': 'application/json', 'Accept': 'application/json']
        def expectNames = ["EMR API", "Kenya EMR"] as Set
        def gson = new Gson();

        sendRequest('/api/search?q=erm', 'GET', headers)

        def json = gson.fromJson(response.contentAsString, Map)
        def suggestions = json.suggestions as Set

        assertEquals(200, response.status)
        assertTrue(suggestions.contains("emr"))
    }

}
