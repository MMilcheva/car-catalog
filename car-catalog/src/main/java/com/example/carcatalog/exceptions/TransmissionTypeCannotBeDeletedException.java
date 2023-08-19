package com.example.carcatalog.exceptions;

public class TransmissionTypeCannotBeDeletedException extends RuntimeException {

    public TransmissionTypeCannotBeDeletedException(String type, String attribute, String value) {
        super(String.format("%s with %s %s cannot be deleted. Please archive Transmission type or delete their cars first.", type, attribute, value));
    }
}
