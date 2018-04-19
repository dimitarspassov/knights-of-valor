package com.dspassov.kovapi.web;


public class JSONBoolResponse {

    private boolean response;

    public JSONBoolResponse(boolean response) {
        this.response = response;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}