package com.example.carcatalog.exceptions;

public class ModelCannotBeDeletedException extends RuntimeException {

    public ModelCannotBeDeletedException(String type, String attribute, String value) {
        super(String.format("%s with %s %s cannot be deleted. Please archive model or delete their cars first.", type, attribute, value));
    }
}
