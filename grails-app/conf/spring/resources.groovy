import org.openmrs.modulus.marshallers.*
import org.openmrs.modulus.oauth.ModulusUserApprovalHandler
import org.openmrs.modulus.oauth.OpenMrsIdApi
import org.openmrs.modulus.oauth.RestApiAwareLoginUrlAuthenticationEntryPoint
import org.openmrs.modulus.servlet.LegacyFindModule

// Place your Spring DSL code here
beans = {

    legacyFindModule(LegacyFindModule)

    // OAuth & Spring Security
    userApprovalHandler(ModulusUserApprovalHandler)
    authenticationEntryPoint(RestApiAwareLoginUrlAuthenticationEntryPoint) {
        loginFormUrl = '/login'
    }
    openMrsIdApi(OpenMrsIdApi)


}
