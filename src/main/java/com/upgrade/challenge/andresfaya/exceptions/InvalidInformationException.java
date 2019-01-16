package com.upgrade.challenge.andresfaya.exceptions;

public class InvalidInformationException extends RuntimeException {
    public InvalidInformationException(String message) {
        super("The information provided is invalid.\n" + message);
    }
}
