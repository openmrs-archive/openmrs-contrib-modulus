package org.openmrs.modulus.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by herooftime on 2/23/14.
 */
class CorsFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException { }

    public void destroy() { }

    public void doFilter(
            ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        ((HttpServletResponse) response).addHeader(
                "Access-Control-Allow-Origin", "*"
        );
        chain.doFilter(request, response);
    }
}