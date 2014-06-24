package org.openmrs.modulus.oauth

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint

import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by Elliott Williams on 6/9/14.
 */
class RestApiAwareLoginUrlAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {
    protected String ajaxLoginFormUrl;

    public RestApiAwareLoginUrlAuthenticationEntryPoint() {
    }

    public RestApiAwareLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * Rather than try to redirect to a login form, always send a 401 Unauthorized
     * response. Since we *only* provide a REST API, this is the only needed
     * behavior.
     */
    @Override
    void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}