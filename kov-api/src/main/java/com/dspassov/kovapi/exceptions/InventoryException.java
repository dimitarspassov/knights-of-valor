package com.dspassov.kovapi.exceptions;

import com.dspassov.kovapi.web.ResponseMessageConstants;

@GameException
public class InventoryException extends RuntimeException {


    public InventoryException(String message) {
        super(message);
    }

    public InventoryException() {
        super(ResponseMessageConstants.INVENTORY_FULL);
    }
}
