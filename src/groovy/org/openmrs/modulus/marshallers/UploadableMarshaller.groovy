package org.openmrs.modulus.marshallers

import grails.converters.JSON
import org.openmrs.modulus.Uploadable

/**
 * Created by herooftime on 2/12/14.
 */
class UploadableMarshaller {

    void register() {
        JSON.registerObjectMarshaller(Uploadable) { Uploadable instance ->
            return instance.properties.minus([rawFile: instance.rawFile, path: instance.path])
        }
    }
}
