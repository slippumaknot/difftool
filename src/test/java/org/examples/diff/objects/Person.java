package org.examples.diff.objects;

import java.util.List;

public class Person {

    private String firstName;
    private String lastName;
    private int age;
    private Subscription subscription;
    private List<String> services;
    private List<Vehicle> vehicles;
    private Integer ageObj;

    public Person(String firstName, String lastName, int age, Subscription subscription, List<String> services, List<Vehicle> vehicles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.subscription = subscription;
        this.services = services;
        this.vehicles = vehicles;
        this.ageObj = age;
    }
}