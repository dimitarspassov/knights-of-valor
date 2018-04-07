package com.dspassov.kovapi.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    private Gson gson;

    protected String success(String message) {
        JSONResponse response = new JSONResponse(true, message);
        return this.gson.toJson(response);
    }

    protected String error(String message) {
        JSONResponse response = new JSONResponse(false, message);
        return this.gson.toJson(response);
    }

}
