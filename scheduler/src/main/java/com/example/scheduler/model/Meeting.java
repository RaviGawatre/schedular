package com.example.scheduler.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Meeting {

    private final LocalDateTime startTime;
    private final Set<Person> attendees;

    public Meeting(LocalDateTime startTime, Set<Person> attendees) {
        this.startTime = startTime;
        this.attendees = attendees;
    }

    public LocalDateTime getStartTime() { return startTime; }
    public Set<Person> getAttendees() { return attendees; }

    @Override
    public String toString() {
        return "Meeting at " + startTime + " with " + attendees;
    }

}
