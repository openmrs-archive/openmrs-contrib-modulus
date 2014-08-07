package org.openmrs.modulus

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.Validateable

import static org.springframework.http.HttpStatus.*

class ModuleTagController {

    static responseFormats = ['json']

    def moduleService

    def beforeInterceptor = {

        if (!params.moduleId) {
            render text: "Module ID could not be found in the request URL", status: BAD_REQUEST
            return false
        } else if (!Module.exists(params.moduleId)) {
            render text: "Module with ID ${params.moduleId} could not be found", status: NOT_FOUND
            return false
        } else {
            params.module = Module.get(params.moduleId)
            return true
        }
    }

    /**
     * Show a list of tags this module is tagged with
     * @return
     */
    def show() {
        Module m = params.module

        respond m.tags
    }

    /**
     * Add a tag to the list. If its name matches a pre-existing tag, add that tag. Otherwise, create a new tag
     * and add it.
     * @return
     */
    @Secured("ROLE_USER")
    def save(TagLookupCommand cmd) {

        Module m = params.module

        Tag t = Tag.findByName(cmd.name)

        if (!t) {
            t = new Tag(cmd.properties.findAll { it != null })
        }

        t.addToModules m
        t.save flush:true

        respond t, [status: CREATED]
    }

    /**
     * Delete a tag from a module.
     * @return
     */
    @Secured("ROLE_USER")
    def delete(TagDeleteCommand cmd) {
        Module m = params.module

        def t = Tag.get(cmd.id)
        if (t == null) {
            render "Could not find tag with ID ${params.id}", status: NOT_FOUND
            return
        }

        t.removeFromModules m
        t.save flush:true

        render status: NO_CONTENT
    }

}

@Validateable
class TagLookupCommand {
    String name
    String description
    String color

    static constraints = {
        name nullable: false
        description nullable: true
        color nullable: true
    }
}

@Validateable
class TagDeleteCommand {
    Long id
}