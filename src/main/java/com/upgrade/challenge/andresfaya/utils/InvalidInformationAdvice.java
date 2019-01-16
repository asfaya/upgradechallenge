package com.upgrade.challenge.andresfaya.utils;

import com.upgrade.challenge.andresfaya.exceptions.InvalidInformationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class InvalidInformationAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidInformationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidInformationAdvice(InvalidInformationException ex) {
        return ex.getMessage();
    }
}
