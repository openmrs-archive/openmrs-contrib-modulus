package org.openmrs.modulus.oauth

import grails.plugin.springsecurity.oauth.OAuthToken
import org.scribe.model.Token

/**
 * Created by Elliott Williams on 5/8/14.
 */
class OpenMrsIdOAuthToken extends OAuthToken {
    public static final String PROVIDER_NAME = 'openmrsid'

    String username

    OpenMrsIdOAuthToken(Token scribeToken, username) {
        super(scribeToken)
        this.username = username
        this.principal = username
    }

    String getSocialId() { username }
    String getScreenName() { username }
    String getProviderName() { PROVIDER_NAME }

}
