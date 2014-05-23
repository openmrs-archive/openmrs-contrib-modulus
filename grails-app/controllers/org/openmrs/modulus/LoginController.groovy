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

        if (getTransaction()) {
            def transaction = getTransaction()
        } else {
            return
        }

        def sessionKey = oauthService.findSessionKeyForAccessToken(transaction.provider)
        if (!session[sessionKey]) {
            renderError 500, "No OAuth token in the session for provider '${transaction.provider}'!"
            return
        }

        // Create the relevant authentication token and attempt to log in.
        OAuthToken oAuthToken = createAuthToken(transaction.provider, session[sessionKey])

        // If this OAuth token does not represent an internal user, create a
        // user account to match the token.
        if (!oAuthToken.principal instanceof GrailsUser) {

            User user = new User(username: oAuthToken.socialId)
            user.addTooAuthIDs(provider: oAuthToken.providerName,
                                accessToken: oAuthToken.socialId)

            user.save(failOnError: true)

            oAuthToken = updateOAuthToken(oAuthToken, user)
        }

        // Set the authentication context to this token, effectively logging
        // in.
        session.removeAttribute SPRING_SECURITY_OAUTH_TOKEN
        SecurityContextHolder.context.authentication = oAuthToken

        // Redirect to the *internal* OAuth authorization url to continue the
        // login process.
        redirect url: internalOauthUrl(transaction)
    }

    def failure() {

        if (getTransaction()) {
            def transaction = getTransaction()
        } else {
            return
        }

        redirect url: transaction.redirect_uri

    }


    private def internalOauthUrl(def transaction) {
        def serverURL = grailsApplication.config.grails.serverURL
        def internal_oauth = new URIBuilder(serverURL)

        internal_oauth.with {
            path = "/oauth/authorize"
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