package com.igladkikh.warehouse.exception;

public class DataConflictException extends RuntimeException {

    public DataConflictException(String message) {
        super(message);
    }
}