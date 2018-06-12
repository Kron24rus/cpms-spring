package com.fireway.cpms.util;

public enum SessionAttribute {
    LOGGED_USER_ID("loggedUserId");

    private final String name;

    SessionAttribute(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
