package org.openmrs.modulus.oauth

import grails.converters.JSON
import org.codehaus.groovy.grails.web.converters.exceptions.ConverterException
import org.codehaus.groovy.grails.web.json.JSONObject
import org.scribe.builder.api.DefaultApi20
import org.scribe.exceptions.OAuthException
import org.scribe.extractors.AccessTokenExtractor
import org.scribe.extractors.TokenExtractor20Impl
import org.scribe.model.OAuthConfig
import org.scribe.model.OAuthConstants
import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.model.Verifier
import org.scribe.oauth.OAuth20ServiceImpl
import org.scribe.oauth.OAuthService
import org.scribe.utils.OAuthEncoder
import org.scribe.utils.Preconditions

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Elliott Williams on 5/6/14.
 */

/**
 * OAuth 2 / OpenID Connect implementation for authenticating and
 * authorizing with OpenMRS ID.
 */
class OpenMrsIdApi extends DefaultApi20 {
    public static final String HOST = "http://localhost:3000"
    private static final String AUTHORIZE_URL = "$HOST/oauth/" +
            "authorize?response_type=code&client_id=%s&redirect_uri=%s&scope=%s"

    public String getAuthorizationUrl(OAuthConfig config) {
        return String.format(AUTHORIZE_URL, config.getApiKey(),
                    OAuthEncoder.encode(config.getCallback()),
                    OAuthEncoder.encode(config.getScope()))
    }

    public String getAccessTokenEndpoint() { "$HOST/oauth/token" }

    @Override
    public Verb getAccessTokenVerb() { Verb.POST }

    @Override
    OAuthService createService(OAuthConfig config) {
        return new OpenMrsIdOAuth2Service(this, config)
    }

    @Override
    AccessTokenExtractor getAccessTokenExtractor() {
        return new OpenMrsIdTokenExtractor20()
    }

    private class OpenMrsIdOAuth2Service extends OAuth20ServiceImpl {

        private final DefaultApi20 api;
        private final OAuthConfig config;
        private static final String VERSION = "2.0";

        OpenMrsIdOAuth2Service(DefaultApi20 api, OAuthConfig config) {
            super(api, config)
            this.api = api;
            this.config = config;
        }

        @Override
        String getVersion() { VERSION }

        @Override
        Token getAccessToken(Token requestToken, Verifier verifier) {
            OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint());
            config.getApiKey()

//            request.addHeader('Authorization', 'Basic ' +
//                "${config.getApiKey()}:${config.getApiSecret()}".bytes
//                        .encodeBase64().toString())

            request.addBodyParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
            request.addBodyParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
            request.addBodyParameter('grant_type', 'authorization_code')
            request.addBodyParameter(OAuthConstants.CODE, verifier.getValue())
            request.addBodyParameter(OAuthConstants.REDIRECT_URI, config.getCallback())
            if(config.hasScope()) request.addQuerystringParameter(OAuthConstants.SCOPE, config.getScope())
            Response response = request.send()
            return api.getAccessTokenExtractor().extract(response.getBody())
        }
    }

    private class OpenMrsIdTokenExtractor20 extends TokenExtractor20Impl {
        @Override
        Token extract(String response) {
            Preconditions.checkEmptyString(response, "Response body is incorrect. Can't extract a token from an empty string")

            JSONObject responseObject = JSON.parse(response)
            if (responseObject.has('access_token')) {
                String token = responseObject.get('access_token')
                return new Token(token, "", response)
            } else {
                throw new OAuthException("Response body is incorrect. Can't extract a token from this: '" + response + "'", null)
            }
        }
    }


}