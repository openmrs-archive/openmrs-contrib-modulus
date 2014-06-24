package org.openmrs.modulus.oauth

import grails.plugin.springsecurity.oauth.OAuthToken
import org.scribe.model.Token

/**
 * Created by Elliott Williams on 5/8/14.
 */
class OpenMrsIdOAuthToken extends OAuthToken {
    public static final String PROVIDER_NAME = 'openmrsid'

    String username
    String displayName

    OpenMrsIdOAuthToken(Token scribeToken, username, displayName) {
        super(scribeToken)
        this.username = username
        this.displayName = displayName
        this.principal = username
    }

    String getSocialId() { username }
    String getScreenName() { displayName }
    String getProviderName() { PROVIDER_NAME }

}
