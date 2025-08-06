package com.ludothecasharemesh.bookservice.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

//http://fruzenshtein.com/spring-jpa-data-hibernate-mysql/
//http://blog.sapiensworks.com/post/2014/06/02/The-Repository-Pattern-For-Dummies.aspx

@Entity
public class Book {

    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String author;
    private String publisher;
    private String language;
    private int year;
    private String topic;
    private String genre;
    private int ownerID;
    @Value("true")
    private boolean isAvailable;

    public Book() {
    }

    public Book(int id, String title, String author, String publisher, String language, int year, String topic, String genre, int ownerID, boolean isAvailable) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.language = language;
        this.year = year;
        this.topic = topic;
        this.genre = genre;
        this.ownerID = ownerID;
        this.isAvailable = isAvailable;
    }

    public Book(int id, String title, String author, String publisher, String language, int year, String topic, String genre, int ownerID) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.language = language;
        this.year = year;
        this.topic = topic;
        this.genre = genre;
        this.ownerID = ownerID;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {  //do error checking that positive year is entered?
        this.year = year;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
