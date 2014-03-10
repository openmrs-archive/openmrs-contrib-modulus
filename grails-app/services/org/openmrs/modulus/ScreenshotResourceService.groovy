package org.openmrs.modulus

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class ScreenshotResourceService {

    def create(Screenshot dto) {
        dto.save()
    }

    def read(id) {
        def obj = Screenshot.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(Screenshot.class, id)
        }
        obj
    }

    def readAll() {
        Screenshot.findAll()
    }

    def update(Screenshot dto) {
        def obj = Screenshot.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(Screenshot.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    void delete(id) {
        def obj = Screenshot.get(id)
        if (obj) {
            obj.delete()
        }
    }
}
