package org.openmrs.modulus.oauth

import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.support.aware.GrailsApplicationAware
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.AuthorizationRequest
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler

/**
 * Created by Elliott Williams on 5/22/14.
 */
class ModulusUserApprovalHandler extends DefaultUserApprovalHandler
        implements UserApprovalHandler, GrailsApplicationAware {

    private static final log = LogFactory.getLog(this)

    def grailsApplication

    public void setGrailsApplication(GrailsApplication app) {
        grailsApplication = app
    }

    /**
     * If the client in question is marked as "preApproved" in the oauth provider
     * configuration, always return true.
     * @param authorizationRequest The authorization request.
     * @param userAuthentication the current user authentication
     * @return Whether the specified request has been approved by the current user.
     */
    boolean isApproved(AuthorizationRequest authorizationRequest,
                       Authentication userAuthentication) {

        log.debug "isApproved called !"

        ClientDetailsService clientDetailsService = grailsApplication.getMainContext()
                .getBean("clientDetailsService")

        def client = clientDetailsService.loadClientByClientId(authorizationRequest.clientId)

        log.debug "client id = ${client.clientId}, additional = ${client.additionalInformation}"

        if (client.additionalInformation?.preApproved) {
            log.debug "client pre-approved"
            return true
        } else {
            log.debug "client NOT pre-approved"
            super.isApproved(authorizationRequest, userAuthentication)
        }


    }
}
