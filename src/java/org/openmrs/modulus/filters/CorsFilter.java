package org.openmrs.modulus.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by herooftime on 2/23/14.
 */
public class CorsFilter implements Filter {
    public void init(FilterConfig fConfig) throws ServletException { }

    public void destroy() { }

    public void doFilter(
            ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        res.addHeader("Access-Control-Allow-Origin", "*");


        // Handle preflight requests
        if (req.getMethod().equals("OPTIONS") && !req.getHeader("Access-Control-Request-Method").isEmpty()) {
            res.addHeader("Access-Control-Allow-Methods", req.getHeader("Access-Control-Request-Method"));
            res.addHeader("Access-Control-Allow-Headers", req.getHeader("Access-Control-Request-Headers"));

            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }
}