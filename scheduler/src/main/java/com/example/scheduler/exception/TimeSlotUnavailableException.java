package com.example.scheduler.exception;

import java.time.LocalDateTime;

public class TimeSlotUnavailableException extends RuntimeException {

    public TimeSlotUnavailableException(String email, LocalDateTime time) {
        super("Time slot unavailable for " + email + " at " + time);
    }

}
