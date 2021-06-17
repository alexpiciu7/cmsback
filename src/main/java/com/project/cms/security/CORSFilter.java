package com.project.cms.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

public class CORSFilter implements Filter {

    private static final String HEADERS_MAX_AGE_VALUE = "3600";

    private static final String COMMA = ",";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
        ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest rf = ((HttpServletRequest) req);

        final String origin = rf.getHeader(HttpHeaders.ORIGIN);

        if (origin != null) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        }

        String allowedMethods = String.join(COMMA, Arrays.asList(HttpMethod.POST.name(),
            HttpMethod.GET.name(),
            HttpMethod.PUT.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.PATCH.name()));

        String allowedHeadersString = String.join(COMMA, Arrays.asList(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD,
            HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS,
            HttpHeaders.ORIGIN,
            HttpHeaders.AUTHORIZATION,
            HttpHeaders.ACCEPT,
            HttpHeaders.CONTENT_TYPE));

        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, allowedMethods);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, Boolean.TRUE.toString());
        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, HEADERS_MAX_AGE_VALUE);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, allowedHeadersString);
        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);

        if (HttpMethod.OPTIONS.name().equals(rf.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
