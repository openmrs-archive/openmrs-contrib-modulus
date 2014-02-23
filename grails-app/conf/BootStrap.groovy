
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

    def init = { servletContext ->
        // Get spring
        def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
        // Custom marshalling
        springContext.getBean( "customObjectMarshallers" ).register()

    }
    def destroy = {
    }
}
