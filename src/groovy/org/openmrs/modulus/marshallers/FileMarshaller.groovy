package org.openmrs.modulus.marshallers

import grails.converters.JSON

/**
 * Created by herooftime on 2/7/14.
 */
class FileMarshaller {

    void register() {
        JSON.registerObjectMarshaller(org.openmrs.modulus.File) { org.openmrs.modulus.File file ->
            return [
                    id: file.id,
                    filename: file.filename
            ]
        }
    }
}
