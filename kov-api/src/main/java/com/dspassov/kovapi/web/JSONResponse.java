package com.dspassov.kovapi.web;


public class JSONResponse {

    private boolean success;
    private String message;

    public JSONResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

}