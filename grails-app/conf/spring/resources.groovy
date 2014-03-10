import com.wordnik.swagger.jaxrs.config.BeanConfig
import grails.rest.render.json.JsonCollectionRenderer
import grails.rest.render.json.JsonRenderer
import grails.rest.render.xml.XmlCollectionRenderer
import grails.rest.render.xml.XmlRenderer
import org.openmrs.modulus.marshallers.*

// Place your Spring DSL code here
beans = {
    customObjectMarshallers( CustomObjectMarshallers ) {
        marshallers = [
                new UploadableMarshaller(),
                new ReleaseMarshaller()
        ]
    }

    swaggerConfig(BeanConfig) {
        resourcePackage = "org.openmrs.modulus.resources"
        version = "0.1"
        basePath = "${grailsApplication.config.grails.serverURL}/api"
        title = "Modulus"
        description = "Backend of the OpenMRS Module Repository"
        contact = "elliott@openmrs.org"
        license = "MPLv2"
        licenseUrl = "http://www.mozilla.org/MPL/2.0/"
        scan = true
    }
}
