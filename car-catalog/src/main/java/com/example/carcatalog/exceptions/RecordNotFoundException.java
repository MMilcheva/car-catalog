package com.example.carcatalog.exceptions;

public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String message){
        super(message);
    }

}
