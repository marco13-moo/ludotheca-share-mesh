package com.ludothecasharemesh.lendingservice.controller;

import com.ludothecasharemesh.lendingservice.entity.Loan;
import com.ludothecasharemesh.lendingservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

//See more about controllers at: https://www.baeldung.com/spring-controller-vs-restcontroller
@RestController //"Every request handling method of the controller class automatically serializes return objects into HttpResponse"
@RequestMapping("/loan")  //loan noun has to come before final end-point text
public class LoanController {

 /*
 * Through compartmentalisation between service, controller, repository and entity we are able
 * to alter aspects of the software in a more organized/easier manner than with no architectural style
 * */

     /*
    A note on Autowired can be found here: https://www.baeldung.com/spring-autowire
    "The Spring framework enables automatic dependency injection. In other words, by declaring all the
     bean dependencies in a Spring configuration file, Spring container can autowire relationships between
     collaborating beans. This is called Spring bean autowiring."
    * */

    @Autowired
    private LoanService service; //instanciate new loan service

    @GetMapping("")
    public void twohundred() {
        System.out.println("Gives 200 response");
    }


    @PostMapping("/checkOutLoan") //checkOutLoan
    public Loan checkOutLoan(@RequestBody Loan loan){ //takes in member object, in JSON with @RequestBody
        return service.checkOutLoan(loan); //calls checkOutLoan method in loanService
    }

    @PostMapping("/returnLoanById/{id}") //return a loan specific to the loanID
    public Loan returnLoan(@PathVariable int id){

        return service.returnLoan(id); //ReturnLoan method call in LoanService
    }

    @GetMapping("/loans") //lists all loans
    public List<Loan> findAllLoans() {
        return service.getLoans();
    }

    @GetMapping("/loanById/{id}") //find a loan specific to the loanID
    public Loan findLoanById(@PathVariable int id) {
        return service.getLoanById(id);
    }

    @GetMapping("/loanByOwnerId/{ownerId}") //get loans relating to the Owner of an Item
    public List<Loan> findLoansByOwner(@PathVariable int ownerId) {
        return service.getLoansByOwner(ownerId);
    }

    @GetMapping("/loanByBorrowerId/{borrowerId}") ////get loans relating to the Borrower of an Item
    public List<Loan> findLoansByBorrower(@PathVariable int borrowerId) {
        return service.getLoansByBorrower(borrowerId);
    }

    @GetMapping("/loanByItemId/{itemId}") //get loans by an item ID
    public List<Loan> findLoansByItemID(@PathVariable int itemId) {
        return service.getLoansByItemID(itemId);
    }

    @GetMapping("/loanByItemType/{itemType}") //get all loans by a specific item type
    public List<Loan> findLoansByItemType(@PathVariable String itemType) {
        return service.getLoansByItemType(itemType);
    }


    //See these links if you want to learn more about dealing with Dates, REST and Spring
    //https://www.baeldung.com/spring-date-parameters
    //https://stackoverflow.com/questions/51072142/how-to-pass-java-date-as-path-variable-in-a-rest-get-call

    @GetMapping("/loanByCheckOutDate/{checkOutDate}") //get loans with a specific checkoutdate
    public List<Loan> findLoansByCheckOutDate(@PathVariable("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate) {
        return service.getLoansByCheckOutDate(checkOutDate);
    }

    @GetMapping("/loanBeforeCheckOutDate/{checkOutDate}") //get loans before a specific checkoutdate
    public List<Loan> findLoansBeforeCheckOutDate(@PathVariable("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate) {
        return service.getLoansBeforeCheckOutDate(checkOutDate);
    }

    @GetMapping("/loanAfterCheckOutDate/{checkOutDate}") //get loans after a specific checkoutdate
    public List<Loan> findLoansAfterCheckOutDate(@PathVariable("checkOutDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date checkOutDate) {
        return service.getLoansAfterCheckOutDate(checkOutDate);
    }

    @GetMapping("/loanByReturnDate/{returnDate}") //get loans by a specific returnDate
    public List<Loan> findLoansByReturnDate(@PathVariable("returnDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnDate) {
        return service.getLoansByReturnDate(returnDate);
    }

    @GetMapping("/loanBeforeReturnDate/{returnDate}") //get loans before a specific returnDate
    public List<Loan> findLoansBeforeReturnDate(@PathVariable("returnDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnDate) {
        return service.getLoansBeforeReturnDate(returnDate);
    }

    @GetMapping("/loanAfterReturnDate/{returnDate}") //get loans after a specific returnDate
    public List<Loan> findLoansAfterReturnDate(@PathVariable("returnDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date returnDate) {
        return service.getLoansAfterReturnDate(returnDate);
    }


    @GetMapping("/returnedLoans") //lists all loans that are no longer active
    public List<Loan> findReturnedLoans() {
        return service.findReturnedLoans();
    }

    @GetMapping("/nonreturnedLoans") //lists all loans that are active
    public List<Loan> findnonReturnedLoans() {
        return service.findnonReturnedLoans();
    }


    @GetMapping("/checkedOutLoans") //list all loans that have been checked out
    public List<Loan> findCheckedOutLoans() {
        return service.findCheckedOutLoans();
    }

    @GetMapping("/nonCheckedOutLoans") //list all loans that have not been checked out
    public List<Loan> findnonCheckedOutLoans() {
        return service.findnonCheckedOutLoans();
    }

    //Loan cannot be deleted for auditing purposes
    /*
    @DeleteMapping("/deleteById/{id}") //delete by ID
    public String deleteLoan(@PathVariable int id) {
        return service.deleteLoan(id);
    }

     */

}
