package com.example.scheduler.demo;

import com.example.scheduler.model.Meeting;
import com.example.scheduler.service.SchedulingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class SchedulingAppDemo {
    public static void main(String[] args) {
        SchedulingService service = new SchedulingService();

        service.createPerson("Alice", "alice@example.com");
        service.createPerson("Bob", "bob@example.com");
        service.createPerson("Charlie", "charlie@example.com");

        LocalDateTime meetingTime = LocalDateTime.now().withMinute(0).withSecond(0).plusHours(1);
        service.createMeeting(meetingTime, Set.of("alice@example.com", "bob@example.com"));

        LocalDateTime meetingTime2 = LocalDateTime.now().plusHours(2).withMinute(0).withSecond(0).plusHours(1);
        service.createMeeting(meetingTime2, Set.of("charlie@example.com", "bob@example.com"));

        System.out.println("\nAlice's schedule:");
        for (Meeting m : service.getSchedule("alice@example.com")) {
            System.out.println(m);
        }

        System.out.println("\nBob's schedule:");
        for (Meeting m : service.getSchedule("bob@example.com")) {
            System.out.println(m);
        }

        System.out.println("\nCharlie's schedule:");
        for (Meeting m : service.getSchedule("charlie@example.com")) {
            System.out.println(m);
        }

        System.out.println("\nSuggested time slots for Alice, Bob, Charlie:");
        List<LocalDateTime> slots = service.suggestTimeSlots(Set.of(
                "alice@example.com", "bob@example.com", "charlie@example.com"
        ), meetingTime, 5);
        slots.forEach(System.out::println);
    }
}
