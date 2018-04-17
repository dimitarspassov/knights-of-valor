package com.dspassov.kovapi.exceptions;

import com.dspassov.kovapi.web.ResponseMessageConstants;

public class InventoryFullException extends RuntimeException {


    public InventoryFullException(String message) {
        super(message);
    }

    public InventoryFullException() {
        super(ResponseMessageConstants.INVENTORY_FULL);
    }
}
