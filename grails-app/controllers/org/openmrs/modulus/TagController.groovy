package org.openmrs.modulus

import grails.rest.RestfulController

import static org.springframework.http.HttpStatus.NOT_FOUND

class TagController extends RestfulController {

    static responseFormats = ['json']

    Class<Tag> resource

    TagController() {
        super(Tag)
        resource = Tag
    }

    @Override
    Object index(Integer max) {
        return super.index(max)
    }
}
