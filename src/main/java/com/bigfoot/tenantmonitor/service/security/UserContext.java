package com.bigfoot.tenantmonitor.service.security;

public class UserContext {
    private static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        UserContext.userName = userName;
    }
}
