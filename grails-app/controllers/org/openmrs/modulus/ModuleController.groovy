package org.openmrs.modulus

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.RestfulController
import org.codehaus.groovy.grails.web.servlet.HttpHeaders
import org.openmrs.modulus.mixins.PermissionsControllerUtils

import static org.springframework.http.HttpStatus.CREATED

//@Mixin(PermissionsControllerUtils)
class ModuleController extends RestfulController {
    def moduleService
    def springSecurityService

    Class<Module> resource

    static responseFormats = ['json', 'xml']
    ModuleController() {
        super(Module)
        resource = Module
        this.class.mixin PermissionsControllerUtils
    }

    /**
     * Show a module from numeric ID or slug
     * @return response with Module or 404
     */
    @Override
    def show() {
        if (params.id =~ /[0-9]+/) {
            respond queryForResource(params.id)
        } else {
            respond getBySlug(params.id)
        }

    }


    private def getBySlug(String slug) {
        Module.findWhere(slug: slug)
    }

    @Override
    @Secured("ROLE_USER")
    Object save() {
        if(handleReadOnly()) {
            return
        }

        def instance = new Module(params)

        instance.validate()
        if (instance.hasErrors()) {
            respond instance.errors, view:'create' // STATUS CODE 422
            return
        }

        // Add current user as the owner and maintainer
        User current = springSecurityService.getCurrentUser()
        instance.maintainers = [current]
        instance.owner = current.username

        instance.save flush:true

        response.addHeader(HttpHeaders.LOCATION,
                g.createLink(
                        resource: this.controllerName, action: 'show',id: instance.id, absolute: true,
                        namespace: hasProperty('namespace') ? this.namespace : null ))
        respond instance, [status: CREATED]

    }

    @Override
    @Secured("ROLE_USER")
    Object update() {
        def module = queryForResource(params.id)
        if (unauthorized(module)) return

        return super.update()
    }

    @Override
    @Secured("ROLE_USER")
    Object delete() {
        def module = queryForResource(params.id)
        if (!module) return notFound()
        if (unauthorized(module)) return

        return super.delete()
    }

    @Override
    protected Object queryForResource(Serializable id) {
        moduleService.get(id)
    }

}
