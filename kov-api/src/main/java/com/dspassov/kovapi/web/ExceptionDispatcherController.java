package com.dspassov.kovapi.web;


import com.dspassov.kovapi.exceptions.GameException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionDispatcherController extends BaseController {

    @ExceptionHandler(value = Exception.class)
    public String handleGenericException(Exception e) {

        if (e.getClass().isAnnotationPresent(GameException.class)) {
            return this.error(e.getMessage());
        }

        e.printStackTrace();
        return this.error(ResponseMessageConstants.GENERIC_ERROR);
    }


}
