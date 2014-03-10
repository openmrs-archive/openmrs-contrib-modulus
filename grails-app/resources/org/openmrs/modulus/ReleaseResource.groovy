package org.openmrs.modulus

import javax.ws.rs.*

@Path('/api/v2/releases')
@Consumes(['application/xml','application/json'])
@Produces(['application/xml','application/json'])
class ReleaseResource {



    @GET
    String readAll() {
        Release.findAll()
    }
}
