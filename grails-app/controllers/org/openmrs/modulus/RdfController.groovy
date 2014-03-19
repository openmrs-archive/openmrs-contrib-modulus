package org.openmrs.modulus

class RdfController {

    def index() {}

    /**
     * Generate an RDF file for the specified module
     */
    def showModuleRdf() {
        String id = params.id

        Module module
        if (id.isNumber()) {
            module = Module.get(Integer.parseInt(id))
        } else {
            module = Module.findByLegacyIdOrSlug(id, id)
        }

        if (!module) {
            render view: "/404", status: 404
        } else {
            render view: "update.rdf", model: [module: module], contentType: "text/xml; charset=utf-8"
        }
    }
}
