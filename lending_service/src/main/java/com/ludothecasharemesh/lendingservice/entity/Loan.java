package com.ludothecasharemesh.lendingservice.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Entity
public class Loan {


    @Id
    @GeneratedValue
    private int id;
    private int ownerID;
    private int borrowerID; //check borrowerID cooresponds to itemID
    private int itemID;
    private String itemType;

    @Temporal(TemporalType.DATE) //https://www.baeldung.com/spring-data-jpa-query-by-date
    private Date checkOutDate;

    @Temporal(TemporalType.DATE)
    private Date returnDate;

    private boolean isReturned;
    private boolean isCheckedOut;


    public Loan() {
    }


    public Loan(int id, int ownerID, int borrowerID, int itemID, String itemType, Date checkOutDate, Date returnDate, boolean isReturned, boolean isCheckedOut) {
        this.id = id;
        this.ownerID = ownerID;
        this.borrowerID = borrowerID;
        this.itemID = itemID;
        this.itemType = itemType;
        this.checkOutDate = checkOutDate;
        this.returnDate = returnDate;
        this.isReturned = isReturned;
        this.isCheckedOut = isCheckedOut;
    }


    public Loan(int id, int ownerID, int borrowerID, int itemID, String itemType) {
        this.id = id;
        this.ownerID = ownerID;
        this.borrowerID = borrowerID;
        this.itemID = itemID;
        this.itemType = itemType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getBorrowerID() {
        return borrowerID;
    }

    public void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }


}
