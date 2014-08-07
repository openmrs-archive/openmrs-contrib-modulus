package org.openmrs.modulus

import grails.converters.JSON
import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController

import static org.springframework.http.HttpStatus.NOT_FOUND

class TagController extends RestfulController {

    static responseFormats = ['json']

    Class<Tag> resource

    TagController() {
        super(Tag)
        resource = Tag
    }

    def beforeInterceptor = {
        if (params.moduleId && Module.exists(params.moduleId)) {
            params.module = Module.get(params.moduleId)
        }
    }

    @Override
    Object index(Integer max) {
        JSON.use('tagShow') {
            return super.index(max)
        }
    }

    @Override
    Object show() {
        JSON.use('tagShow') {
            return super.show()
        }
    }

    @Override
    @Secured("ROLE_USER")
    Object save() {
        return super.save()
    }

    @Override
    @Secured("ROLE_USER")
    Object update() {
        return super.update()
    }

    @Override
    @Secured("ROLE_USER")
    Object delete() {
        return super.delete()
    }
}
