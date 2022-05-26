package org.farid.model;

import lombok.Data;

@Data
public class Person {

    String firstName;
    String lastName;
    String email;

    public Person() {
    }

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
