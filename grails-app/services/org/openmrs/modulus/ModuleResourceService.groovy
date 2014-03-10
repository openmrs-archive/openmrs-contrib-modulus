package org.openmrs.modulus

import org.grails.jaxrs.provider.DomainObjectNotFoundException

class ModuleResourceService {

    def create(Module dto) {
        dto.save()
    }

    def read(id) {
        def obj = Module.get(id)
        if (!obj) {
            throw new DomainObjectNotFoundException(Module.class, id)
        }
        obj
    }

    def readFromSlug(slug) {
        def obj = Module.findWhere(slug: slug)
        if (!obj) {
            throw new DomainObjectNotFoundException(Module.class, id)
        }
        obj
    }

    def readAll() {
        Module.findAll()
    }

    def update(Module dto) {
        def obj = Module.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(Module.class, dto.id)
        }
        obj.properties = dto.properties
        obj
    }

    def updateFromDelta(Module dto) {
        def obj = Module.get(dto.id)
        if (!obj) {
            throw new DomainObjectNotFoundException(Module.class, dto.id)
        }
        obj.properties << dto.properties
        obj
    }

    void delete(id) {
        def obj = Module.get(id)
        if (obj) {
            obj.delete()
        }
    }
}
