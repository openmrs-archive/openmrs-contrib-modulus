package org.openmrs.modulus.resources

import com.wordnik.swagger.annotations.*

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.POST
import javax.ws.rs.core.Response
import org.openmrs.modulus.Module

@Path('/api/modules')
@Api(value='/modules', description='Operations on modules within the repository')
@Consumes(['application/json','application/xml'])
@Produces(['application/json','application/xml'])
class ModuleCollectionResource {

    def moduleResourceService

    @POST
    @ApiOperation(value='Create a new module')
    Response create(Module dto) {
        created moduleResourceService.create(dto)
    }

    @GET
    @ApiOperation(value='Get a list of modules')
    // TODO pagination and ranged of modules
    Response readAll() {
        ok moduleResourceService.readAll()
    }

    @Path('/{id}')
    ModuleResource getResource(@PathParam('id') Long id) {
        new ModuleResource(moduleResourceService: moduleResourceService, id:id)
    }
}
