package org.openmrs.modulus

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.POST
import javax.ws.rs.core.Response

@Path('/api/screenshots')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ScreenshotCollectionResource {

    def screenshotResourceService

    @POST
    Response create(Screenshot dto) {
        created screenshotResourceService.create(dto)
    }

    @GET
    Response readAll() {
        ok screenshotResourceService.readAll()
    }

    @Path('/{id}')
    ScreenshotResource getResource(@PathParam('id') Long id) {
        new ScreenshotResource(screenshotResourceService: screenshotResourceService, id:id)
    }
}
