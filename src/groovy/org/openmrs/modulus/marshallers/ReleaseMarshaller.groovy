package org.openmrs.modulus.marshallers

import grails.converters.JSON
import org.openmrs.modulus.Release

/**
 * Created by herooftime on 2/21/14.
 */
class ReleaseMarshaller {
    void register() {
        JSON.registerObjectMarshaller(Release) { Release instance->
            return instance.properties.plus([id: instance.id]).minus([path: instance.path])
        }
    }
}
