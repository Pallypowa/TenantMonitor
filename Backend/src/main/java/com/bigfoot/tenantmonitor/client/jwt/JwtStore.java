package com.bigfoot.tenantmonitor.client.jwt;

public class JwtStore {
    private static String accessToken;
    private static String refreshToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        JwtStore.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        JwtStore.refreshToken = refreshToken;
    }
}
