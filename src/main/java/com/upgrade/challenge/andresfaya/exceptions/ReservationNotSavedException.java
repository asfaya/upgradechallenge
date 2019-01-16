package com.upgrade.challenge.andresfaya.exceptions;

public class ReservationNotSavedException extends RuntimeException  {
    public ReservationNotSavedException(Long id, String message) {
        super("The reservation " + id + " could not be saved. \n" + message);
    }
}

