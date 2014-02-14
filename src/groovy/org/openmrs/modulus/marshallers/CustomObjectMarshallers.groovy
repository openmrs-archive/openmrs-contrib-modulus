/**
 * Created by herooftime on 2/7/14.
 */
package org.openmrs.modulus.marshallers

class CustomObjectMarshallers {

    List marshallers = []

    def register() {
        marshallers.each{ it.register() }
    }
}
