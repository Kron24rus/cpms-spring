package com.fireway.cpms.filter;


import com.fireway.cpms.util.SessionWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizedFilter implements Filter {
    private FilterConfig config = null;

    private final String UTF8 = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        SessionWrapper sessionWrapper = new SessionWrapper(httpServletRequest.getSession(false));
        if (sessionWrapper.isValid()) {
            Integer loggedUserId = sessionWrapper.getLoggedUserId();
            if (loggedUserId != null) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

    @Override
    public void destroy() {

    }
}
