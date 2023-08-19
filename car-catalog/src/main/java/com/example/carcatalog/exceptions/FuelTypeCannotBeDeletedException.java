package com.example.carcatalog.exceptions;

public class FuelTypeCannotBeDeletedException extends RuntimeException {

    public FuelTypeCannotBeDeletedException(String type, String attribute, String value) {
        super(String.format("%s with %s %s cannot be deleted. Please archive Fuel type or delete their cars first.", type, attribute, value));
    }
}
