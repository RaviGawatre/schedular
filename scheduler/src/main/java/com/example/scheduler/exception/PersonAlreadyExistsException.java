package com.example.scheduler.exception;

public class PersonAlreadyExistsException extends RuntimeException {

    public PersonAlreadyExistsException(String email) {
        super("Person with email already exists: " + email);
    }

}
