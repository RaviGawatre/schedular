package com.example.scheduler.model;

import lombok.Data;

@Data
public class Person {

    private final String name;
    private final String email;

    public Person(String name, String email) {
        this.name = name;
        this.email = email.toLowerCase(); // case-insensitive emails
    }

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public boolean equals(Object o) {
        return o instanceof Person && ((Person) o).email.equalsIgnoreCase(this.email);
    }

    @Override
    public int hashCode() {
        return email.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return name + " <" + email + ">";
    }

}
