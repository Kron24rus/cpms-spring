package com.fireway.cpms.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/*")
public class EncodingFilter implements Filter {
    private FilterConfig config = null;

    private final String UTF8 = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {

        servletRequest.setCharacterEncoding(UTF8);
        servletResponse.setCharacterEncoding(UTF8);
        servletResponse.setContentType("application/json");

//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//        SessionWrapper sessionWrapper = new SessionWrapper(httpServletRequest.getSession(false));
//        if (sessionWrapper.isValid()) {
//            Integer loggedUserId = sessionWrapper.getLoggedUserId();
//            if (loggedUserId != null) {
//                System.out.println(loggedUserId);
//            }
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
