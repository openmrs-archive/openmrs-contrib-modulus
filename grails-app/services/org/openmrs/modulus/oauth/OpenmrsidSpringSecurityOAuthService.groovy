package org.openmrs.modulus.oauth

import grails.converters.JSON
import grails.plugin.springsecurity.oauth.OAuthLoginException


class OpenmrsidSpringSecurityOAuthService {

    def oauthService

    def createAuthToken(accessToken) {
        def response = oauthService.getOpenmrsidResource(accessToken,
                "${OpenMrsIdApi.HOST}/oauth/userinfo")

        def user
        try {
            user = JSON.parse(response.body)
        } catch (Exception e) {
            log.error "Error parsing response from OpenMRS ID. Response:\n${response.body}"
            throw new OAuthLoginException("Error parsing response from OpenMRS ID", e)
        }

        if (!user?.uid) {
            log.error "No user id found from OpenMRS ID. Response:\n${response.body}"
            throw new OAuthLoginException("No user id found from OpenMRS ID")
        }

        if (!user?.displayName) {
            log.error "No display name found from OpenMRS ID. Response:\n${response.body}"
            throw new OAuthLoginException("No display name found from OpenMRS ID")
        }

        return new OpenMrsIdOAuthToken(accessToken, user.uid, user.displayName)
    }
}
