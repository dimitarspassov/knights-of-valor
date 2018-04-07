package com.dspassov.kovapi.security;

final class JwtSecurityConstants {

    static final int EXPIRATION_TIME = 3600000;
    static final String SECRET = "K0V-SeCrET";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String HEADER_STRING = "Authorization";


    private JwtSecurityConstants() {
    }

}