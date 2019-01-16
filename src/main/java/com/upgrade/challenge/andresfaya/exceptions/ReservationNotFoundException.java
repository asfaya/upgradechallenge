package com.upgrade.challenge.andresfaya.exceptions;

public class ReservationNotFoundException extends RuntimeException  {
    public ReservationNotFoundException(Long id) {
        super("Could not find reservation " + id);
    }
}
