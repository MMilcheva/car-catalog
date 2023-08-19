package com.example.carcatalog.exceptions;

public class BrandCannotBeDeletedException extends RuntimeException {

    public BrandCannotBeDeletedException(String type, String attribute, String value) {
        super(String.format("%s with %s %s cannot be deleted. Please archive brand or delete their models first.", type, attribute, value));
    }
}
