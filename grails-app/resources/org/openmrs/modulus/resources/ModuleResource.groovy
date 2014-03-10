package org.openmrs.modulus.resources

import com.wordnik.swagger.annotations.ApiOperation
import com.wordnik.swagger.jaxrs.PATCH

import static org.grails.jaxrs.response.Responses.*

import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.PUT
import javax.ws.rs.core.Response
import org.openmrs.modulus.Module

import org.grails.jaxrs.provider.DomainObjectNotFoundException

@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ModuleResource {

    def moduleResourceService
    def id

    @GET
    @ApiOperation(value='Get a module')
    Response read() {
        ok moduleResourceService.read(id)
    }

    @PUT
    @ApiOperation(value='Update a module', notes='The entire module object must be sent. Use PATCH to send a delta')
    Response update(Module dto) {
        dto.id = id
        ok moduleResourceService.update(dto)
    }

    @PATCH
    @ApiOperation(value='Update a module from a delta of changes to be made')
    Response updateFromDelta(Module dto) {
        dto.id = id
        ok moduleResourceService.updateFromDelta(dto)
    }

    @DELETE
    @ApiOperation(value='Delete a module')
    void delete() {
        moduleResourceService.delete(id)
    }
}
