package com.newway.newwayapi.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String ANONYMOUS_USER = "anonymoususer";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BASE64_SECRET = "tSQ/wAmn8iIP3RtGHcLwIvujzswM9sBtIozc0vX90Qlb9bWH9l8codnHcpv0DmdsyIV8H3djxtvEa7iN8up7TA==";
    public static final Long TOKEN_VALIDITY_IN_SECOND = 86400L;
    public static final Long TOKEN_REMEMBER_IN_SECOND = 2592000L;


    private Constants() {
    }
}
