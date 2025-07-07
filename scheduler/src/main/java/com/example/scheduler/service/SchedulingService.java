package com.example.scheduler.service;

import com.example.scheduler.exception.PersonAlreadyExistsException;
import com.example.scheduler.exception.PersonNotFoundException;
import com.example.scheduler.exception.TimeSlotUnavailableException;
import com.example.scheduler.model.Meeting;
import com.example.scheduler.model.Person;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class SchedulingService {

    private final Map<String, Person> persons = new HashMap<>();
    private final List<Meeting> meetings = new ArrayList<>();

    public void createPerson(String name, String email) {
        if (persons.containsKey(email.toLowerCase())) {
            throw new PersonAlreadyExistsException(email);
        }
        persons.put(email.toLowerCase(), new Person(name, email));
    }

    public void createMeeting(LocalDateTime time, Set<String> emails) {
        if (time.getMinute() != 0 || time.getSecond() != 0) {
            throw new IllegalArgumentException("Meeting must start on the hour.");
        }

        Set<Person> attendees = new HashSet<>();
        for (String email : emails) {
            Person person = persons.get(email.toLowerCase());
            if (person == null) throw new PersonNotFoundException(email);
            attendees.add(person);
        }

        for (Meeting m : meetings) {
            if (m.getStartTime().equals(time)) {
                for (Person p : m.getAttendees()) {
                    if (attendees.contains(p)) {
                        throw new TimeSlotUnavailableException(p.getEmail(), time);
                    }
                }
            }
        }

        meetings.add(new Meeting(time, attendees));
    }

    public List<Meeting> getSchedule(String email) {
        Person person = persons.get(email.toLowerCase());
        if (person == null) throw new PersonNotFoundException(email);

        List<Meeting> result = new ArrayList<>();
        for (Meeting m : meetings) {
            if (m.getAttendees().contains(person)) {
                result.add(m);
            }
        }

        result.sort(Comparator.comparing(Meeting::getStartTime));
        return result;
    }

    public List<LocalDateTime> suggestTimeSlots(Set<String> emails, LocalDateTime from, int hoursToCheck) {

        Set<Person> attendees = new HashSet<>();
        for (String email : emails) {
            Person p = persons.get(email.toLowerCase());
            if (p == null) throw new PersonNotFoundException(email);
            attendees.add(p);
        }

        List<LocalDateTime> suggestions = new ArrayList<>();
        for (int i = 0; i < hoursToCheck; i++) {
            LocalDateTime slot = from.plusHours(i).withMinute(0).withSecond(0);
            boolean allAvailable = true;
            for (Meeting m : meetings) {
                if (m.getStartTime().truncatedTo(ChronoUnit.SECONDS).equals(slot.truncatedTo(ChronoUnit.SECONDS))) {
                    for (Person p : m.getAttendees()) {
                        if (attendees.contains(p)) {
                            allAvailable = false;
                            break;
                        }
                    }
                }
                if (!allAvailable) break;
            }
            if (allAvailable) suggestions.add(slot);
        }

        return suggestions;
    }

}
