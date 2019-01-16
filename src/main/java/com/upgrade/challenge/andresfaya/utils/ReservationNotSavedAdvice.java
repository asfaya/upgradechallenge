package com.upgrade.challenge.andresfaya.utils;

import com.upgrade.challenge.andresfaya.exceptions.ReservationNotSavedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReservationNotSavedAdvice {

    @ResponseBody
    @ExceptionHandler(ReservationNotSavedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String reservationNotSavedAdvice(ReservationNotSavedException ex) {
        return ex.getMessage();
    }
}
