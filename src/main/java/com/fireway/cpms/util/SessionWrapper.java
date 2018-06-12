package com.fireway.cpms.util;

import javax.servlet.http.HttpSession;

public class SessionWrapper {
    private HttpSession session;

    public SessionWrapper(HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return session;
    }

    public boolean isValid() {
        return session != null;
    }

    public Object getAttribute(String attributeName) {
        return this.session.getAttribute(attributeName);
    }

    public Object getAttribute(SessionAttribute attribute) {
        return this.getAttribute(attribute.toString());
    }

    public void setAttribute(String attributeName, Object data) {
        this.session.setAttribute(attributeName, data);
    }

    public void setAttribute(SessionAttribute attribute, Object data) {
        this.setAttribute(attribute.toString(), data);
    }

    public Integer getLoggedUserId() {
        return (Integer) this.getAttribute(SessionAttribute.LOGGED_USER_ID);
    }

    public void setLoggedUserId(Integer id) {
        this.setAttribute(SessionAttribute.LOGGED_USER_ID, id);
    }
}
