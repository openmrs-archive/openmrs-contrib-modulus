package org.openmrs.modulus

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.plugin.springsecurity.oauth.OAuthToken
import grails.plugin.springsecurity.userdetails.GrailsUser
import grails.validation.Validateable
import groovyx.net.http.URIBuilder
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.security.core.context.SecurityContextHolder

class LoginController extends SpringSecurityOAuthController {

    /**
     * Directs a generic login request with a specified login provider (e.g.
     * "openmrsid") to the appropriate authorization url.
     * @param opts LoginCommand - provider name
     * @return redirect to appropriate oauth authorization url
     */
    def login(LoginCommand opts) {

        session.transaction = opts

        redirect(controller: "oauth", action: "authenticate", params: [
                provider: opts.provider
        ])
    }

    /**
     * Called after the successful authorization from an OAuth provider.
     * @return redirect to internal OAuth authentication endpoint.
     */
    def success() {

        def transaction = getTransaction()
        if (!transaction) {
            return
        }

        def sessionKey = oauthService.findSessionKeyForAccessToken(transaction.provider)
        if (!session[sessionKey]) {
            renderError 500, "No OAuth token in the session for provider '${transaction.provider}'!"
            return
        }

        /**
         * Create the relevant authentication token and attempt to log in. If
         * the user already exists in Modulus, oAuthToken.principal will
         * contain the user object.
         */
        OAuthToken oAuthToken = createAuthToken(transaction.provider, session[sessionKey])


        /**
         * If oAuthToken.authenticated is false, that means this user has never
         * access Modulus through this OAuth provider. We'll search for a user
         * with the same principal (unique property such as username). If we
         * find one, we'll associate this OAuth entity with that user.
         * Otherwise, we'll create a new user in the system.
         */
        if (!oAuthToken.authenticated) {

            def preexistingUser = User.findByUsername(oAuthToken.principal)

            if (preexistingUser) { // Associate with this OAuth principal
                new OAuthID(provider: oAuthToken.providerName,
                        accessToken: oAuthToken.principal,
                        user: preexistingUser).save()

            } else { // Create new user
                def created = createUser(oAuthToken, oAuthToken.socialId,
                        oAuthToken.credentials, oAuthToken.screenName)

                if (!created) {
                    renderError(500, "Creating internal user failed'")
                }
            }

        }

        // Update properties of the user with data from the provider.
        log.debug("looking up user with principal ${oAuthToken.principal}")
        def user = User.get(oAuthToken.principal.getId())
        user.username = oAuthToken.socialId
        user.fullname = oAuthToken.screenName
        user.save()

        // Set authentication context and redirect to the *internal* OAuth
        // authorization url to continuing the login process.
        authenticateAndRedirect(oAuthToken, internalOauthUrl(transaction))
    }

    def failure() {

        if (getTransaction()) {
            def transaction = getTransaction()
        } else {
            return
        }

        redirect url: transaction.redirect_uri

    }


    protected def internalOauthUrl(def transaction) {
        def serverURL = grailsApplication.config.grails.serverURL
        def internal_oauth = new URIBuilder(serverURL)

        internal_oauth.with {
            path = "$path/oauth/authorize"
            query = transaction.properties
        }

        internal_oauth.toString()
    }

    private def getTransaction() {
        LoginCommand transaction = session.transaction

        if (!transaction) {
            renderError 500, "No login transaction found."
            return false
        } else {
            return transaction
        }
    }

    protected def createUser(OAuthToken oAuthToken, String username, String password, String fullname = null) {

        def config = SpringSecurityUtils.securityConfig

        boolean created = User.withTransaction { status ->
            User user = new User(username: username, password: password, enabled: true, fullname: fullname)
            user.addToOAuthIDs(provider: oAuthToken.providerName, accessToken: oAuthToken.socialId, user: user)

            // updateUser(user, oAuthToken)

            if (!user.validate() || !user.save()) {
                status.setRollbackOnly()
                return false
            }

            for (roleName in config.oauth.registration.roleNames) {
                UserRole.create user, Role.findByAuthority(roleName)
            }

            oAuthToken = updateOAuthToken(oAuthToken, user)
            return true
        }

        return created
    }

}

/**
 * Login request parameters. Should be a normal OAuth2 Authorize request, but
 * with an additional string, "provider", referring to what backend provider
 * service is being used.
 */
@Validateable
class LoginCommand {

    static def grailsApplication

    String provider = "openmrsid"
    String client_id
    String response_type
    String redirect_uri

    static constraints = {
        provider inList: grailsApplication.config.oauth.providers.keySet().asList()
    }
}