package com.upgrade.challenge.andresfaya.utils;

import com.upgrade.challenge.andresfaya.exceptions.ReservationNotCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ReservationNotCreatedAdvice {
    @ResponseBody
    @ExceptionHandler(ReservationNotCreatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String reservationNotCreatedAdvice(ReservationNotCreatedException ex) {
        return ex.getMessage();
    }
}
