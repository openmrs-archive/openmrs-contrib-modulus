package org.openmrs.modulus

import grails.transaction.Transactional

@Transactional
class ModuleService {

    def get(String id) {
        if (id.isNumber()) {
            get(Integer.parseInt(id))
        } else {
            Module.findBySlug(id)
        }
    }

    def get(int id) {
        Module.get(id)
    }
}
