package com.dspassov.kovapi.exceptions;

import com.dspassov.kovapi.web.ResponseMessageConstants;

public class HeroWorkException extends RuntimeException {


    public HeroWorkException(String message) {
        super(message);
    }

    public HeroWorkException() {
        super(ResponseMessageConstants.HERO_AT_WORK);
    }
}
