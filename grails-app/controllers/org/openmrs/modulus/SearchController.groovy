package org.openmrs.modulus

import grails.validation.Validateable
import grails.converters.JSON
import static org.springframework.http.HttpStatus.*
  
class SearchController {

    static responseFormats = ['json', 'xml']

    def searchService

    def search(SearchCommand cmd) {
        if (cmd.hasErrors()) {
            respond cmd.errors, [status: UNPROCESSABLE_ENTITY]
            return
        }
      
        def result = searchService.search(cmd.q, cmd.properties, cmd.complex)

        if (cmd.type == 'tag') {
          JSON.use('tagShow') { respond result }
        } else {
          respond result
        }

    }
}

@Validateable
class SearchCommand {
    String q
    Integer max = 50
    Integer offset = 0
    String type = 'module'
    Boolean complex = false

    static constraints = {
        q nullable: true
        type inList: ['user',  'module', 'tag']
    }
}