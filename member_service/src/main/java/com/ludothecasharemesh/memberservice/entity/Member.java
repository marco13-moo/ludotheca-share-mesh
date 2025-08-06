package com.ludothecasharemesh.memberservice.entity;

import javax.persistence.*;

@Entity //Domain-Driven Design: "Entity—An object that has a persistent identity" C.Richardson Page 150 Microservices Patterns
public class Member{

    @Id
    @GeneratedValue //JPA auto-gens id. Avoids database design complexity
    private int id;
    private String name;
    private String surname;
    private String email;

    public Member() {
    }


    public Member(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
