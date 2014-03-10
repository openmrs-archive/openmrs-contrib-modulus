package org.openmrs.modulus

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.PUT
import javax.ws.rs.core.Response

import org.grails.jaxrs.provider.DomainObjectNotFoundException

@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ScreenshotResource {

    def screenshotResourceService
    def id

    @GET
    Response read() {
        ok screenshotResourceService.read(id)
    }

    @PUT
    Response update(Screenshot dto) {
        dto.id = id
        ok screenshotResourceService.update(dto)
    }

    @DELETE
    void delete() {
        screenshotResourceService.delete(id)
    }
}
