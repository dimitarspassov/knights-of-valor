package com.dspassov.kovapi.exceptions;

import com.dspassov.kovapi.web.ResponseMessageConstants;

@GameException
public class InsufficientFundsException extends RuntimeException {


    public InsufficientFundsException(String message) {
        super(message);
    }

    public InsufficientFundsException() {
        super(ResponseMessageConstants.INSUFFICIENT_FUNDS);
    }
}
