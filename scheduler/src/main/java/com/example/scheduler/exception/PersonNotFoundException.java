package com.example.scheduler.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(String email) {
        super("Person not found with email: " + email);
    }
}
