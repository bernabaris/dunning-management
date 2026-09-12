package com.github.bernabaris.dunningmanagement.exception;

public class DunningAlreadyExistsException extends RuntimeException{
    public DunningAlreadyExistsException(String message) {
        super(message);
    }
}
