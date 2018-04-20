package com.dspassov.kovapi.exceptions;

@GameException
public class IllegalParamException extends RuntimeException{

    public IllegalParamException(String message) {
        super(message);
    }

}
