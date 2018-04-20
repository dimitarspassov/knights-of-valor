package com.dspassov.kovapi.exceptions;

import com.dspassov.kovapi.web.ResponseMessageConstants;

@GameException
public class HeroFightException extends RuntimeException {


    public HeroFightException(String message) {
        super(message);
    }

    public HeroFightException() {
        super(ResponseMessageConstants.HERO_CANNOT_FIGHT);
    }
}
