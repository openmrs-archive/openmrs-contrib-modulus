import org.openmrs.modulus.Release
import org.openmrs.modulus.Role
import org.openmrs.modulus.Uploadable
import org.openmrs.modulus.User
import org.openmrs.modulus.UserRole
import org.springframework.security.oauth2.provider.BaseClientDetails

//import org.openmrs.modulus.auth.AuthUser
//import org.openmrs.modulus.auth.UserRole
//import org.openmrs.modulus.auth.Role
import org.springframework.web.context.support.WebApplicationContextUtils

class BootStrap {

    def grailsApplication
    def searchableService
    def clientDetailsService

    def init = { servletContext ->
        // Get spring
        def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )
        // Custom marshalling
        springContext.getBean( "customObjectMarshallers" ).register()


        if (System.getProperty('modulus.rebuildPaths') == 'true') {
            rebuildPaths()
        }

        initSecurity()

        // Manually start the mirroring process to ensure that it comes after the automated migrations.
        log.info "Performing bulk index"
        searchableService.reindex()
        log.info "Starting mirror service"
        searchableService.startMirroring()

    }
    def destroy = {
    }

    private def rebuildPaths() {
        Uploadable.list().each { Uploadable obj ->

            if (!obj.path) {
                log.info("Not updating Uploadable id=${obj.id} due to null path")
                return
            }

            def matcher = obj.path =~ /(.+)(org\.openmrs\.modulus.+)/
            def upDir = grailsApplication.config.modulus.uploadDestination

            def newPath = upDir + '/' + matcher[0][2]
            obj.path = newPath.replaceAll('//', '/')

            obj.save()
            log.info("Updated the path for Uploadable with id=${obj.id}")
        }

        Release.list().each { Release rel ->
            rel.generateDownloadURL()

            rel.save()
            log.info("Generated new download URL for Release id=${rel.id}")
        }
    }

    private def initSecurity() {

        // Create the Admin and User roles
        def rAdmin = Role.findOrSaveWhere(authority: 'ROLE_ADMIN')
        def rUser = Role.findOrSaveWhere(authority: 'ROLE_USER')

        // Ensure all Users have the user role
        User.list().each { User u ->
            if (!UserRole.get(u.id, rUser.id)) {
                UserRole.create(u, rUser)
            }
        }

    }
}
