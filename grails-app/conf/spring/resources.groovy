import org.openmrs.modulus.marshallers.*
import org.openmrs.modulus.oauth.ModulusUserApprovalHandler
import org.openmrs.modulus.servlet.LegacyFindModule

// Place your Spring DSL code here
beans = {
    customObjectMarshallers( CustomObjectMarshallers ) {
        marshallers = [
                new UploadableMarshaller(),
                new ReleaseMarshaller()
        ]
    }

    legacyFindModule(LegacyFindModule)
    userApprovalHandler(ModulusUserApprovalHandler)
}
