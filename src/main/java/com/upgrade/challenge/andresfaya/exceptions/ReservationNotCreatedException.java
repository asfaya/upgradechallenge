package com.upgrade.challenge.andresfaya.exceptions;

public class ReservationNotCreatedException extends RuntimeException {
    public ReservationNotCreatedException(String message) {
        super("The reservation could not be created.\n" + message);
    }
}
