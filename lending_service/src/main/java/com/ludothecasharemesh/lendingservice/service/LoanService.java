package com.ludothecasharemesh.lendingservice.service;


import com.ludothecasharemesh.lendingservice.entity.Loan;
import com.ludothecasharemesh.lendingservice.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.*;

import java.util.List;

/*
Note how Loan Service differs from the Lending-Service identification on the Eureka Server,
this is to illustrate the macro and micro part of microservices. We still have DDD design pricnciples within each
service internally, and we also macro-decomposition of the system into microservices externally
* */

@Service //We mark beans with @Service to indicate that it's holding the business logic. See more: https://www.baeldung.com/spring-component-repository-service
public class LoanService {

    /*
    A note on Autowired can be found here: https://www.baeldung.com/spring-autowire
    "The Spring framework enables automatic dependency injection. In other words, by declaring all the
     bean dependencies in a Spring configuration file, Spring container can autowire relationships between
     collaborating beans. This is called Spring bean autowiring."
    * */

    @Autowired
    private LoanRepository repository;

    /*
    A note on RestTemplate can be found here: https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html
    Synchronous client to perform HTTP requests, exposing a simple, template method API over underlying
     HTTP client libraries
    * */

    @Autowired
    private RestTemplate template;



    public Loan checkOutLoan(Loan loan){
        Loan tmp = loan; //set tmp to Loan passed in as param
        int loanId = loan.getId();
        String item = loan.getItemType();
        int itemID = loan.getItemID();
        //ItemQuery is a synchronous inter-process communication aspect of the micro-services.
        //We use API composition to maintain isolation with each services Database

        //make sure item exists
       // String ItemQuery = template.getForObject("http://"+item.toUpperCase()+"-SERVICE"+"/"+item.toLowerCase()+"/"+item.toLowerCase()+"ById"+"/"+itemID, String.class);
        String ItemQuery = getEntityExistence(item, itemID);
        System.out.println("ItemQuery"+ItemQuery);
        if(ItemQuery == null){
            tmp = null; //set to null the IPC method returns nill
            System.out.println("tmp1:"+tmp);
        }

        //IPC: make sure book/item is available
        if(tmp != null) { //https://www.concretepage.com/spring/spring-mvc/spring-rest-client-resttemplate-consume-restful-web-service-example-xml-json#:~:text=getForObject()%20%3A%20It%20retrieves%20an,method%20on%20the%20given%20URL.&text=It%20uses%20HTTP%20DELETE%20method,method%20and%20returns%20an%20entity.
           //IPC method for checking service availability
           // String b =  template.getForObject("http://" + tmp.getItemType().toUpperCase() + "-SERVICE"+"/"+item.toLowerCase()+"/check" + tmp.getItemType().toLowerCase() + "Availability" + "/" + tmp.getItemID(), String.class);
            String itemAvailability = getItemAvailability(item, itemID);
            System.out.println("itemAvailability: "+itemAvailability);
           if(itemAvailability == null || itemAvailability.equalsIgnoreCase("false")){ //if book is not available or response is NULL
               tmp = null;
               System.out.println("tmp2:"+tmp);
           }
        }

        //IPC make sure owner exist
        if(tmp != null){
            //IPC method to get owner
            //String ownres = template.getForObject("http://MEMBER-SERVICE/member/memberById/"+tmp.getOwnerID(), String.class);
            String ownres = getEntityExistence("member", tmp.getOwnerID());
            System.out.println("Ownres1: "+ownres);
            if(ownres == null){ //if no owner is returned set tmp to null
                tmp = null;
                System.out.println("tmp3: "+tmp);
            }
        }

        //IPC: make sure borrower exists
        if(tmp != null){
            //IPC method to get borrower
            //String bowres = template.getForObject("http://MEMBER-SERVICE/member/memberById/"+tmp.getBorrowerID(), String.class);
            String bowres = getEntityExistence("member", tmp.getBorrowerID());
            System.out.println("Bowres1: "+bowres);
            if(bowres == null){
                tmp = null;
               System.out.println("tmp4: "+tmp);
            }
        }

        //IPC make sure that owner does own that specific item
        if(tmp != null) {
            //call specific ITEM service with REST API and get ownerID for specific book
            //String ownership = template.getForObject("http://" + item.toUpperCase() + "-SERVICE" + "/" +item.toLowerCase()+ "/getOwnerId" + "/" + tmp.getOwnerID(), String.class);
            String ownership = getOwnership(item, tmp.getItemID());
            System.out.println("Ownership"+ownership);
            int ownerId = Integer.parseInt(ownership);
            if(ownerId != loan.getOwnerID()){
                tmp = null;
                System.out.println("tmp5:"+tmp);
                System.out.println("Sorry, cannot continue as the ownerID you entered does not match the ownerID find in the bookDB");
            }
        }



        //IPC: flag to {book} service that it is unavailable
        if(tmp != null) { //https://www.concretepage.com/spring/spring-mvc/spring-rest-client-resttemplate-consume-restful-web-service-example-xml-json#:~:text=getForObject()%20%3A%20It%20retrieves%20an,method%20on%20the%20given%20URL.&text=It%20uses%20HTTP%20DELETE%20method,method%20and%20returns%20an%20entity.
            flagItemAvailability(item, tmp.getItemID(), false);
            //template.getForObject("http://" + tmp.getItemType().toUpperCase() + "-SERVICE/"+tmp.getItemType().toLowerCase()+"/update" + tmp.getItemType().toLowerCase() + "Availability" + "/" + tmp.getItemID() + "/" + false, String.class);
        }


        //set dates and params
        if(tmp != null) {
            loan.setCheckOutDate(java.sql.Date.valueOf(LocalDate.now()));
            loan.setReturnDate(java.sql.Date.valueOf(LocalDate.now().plusDays(14)));
            loan.setCheckedOut(true);
            loan.setReturned(false);
            System.out.println("set dates");
        }

        if(tmp != null){
            repository.save(loan); //save loan once all the conditions have been met
            System.out.println("saved");
        }
        System.out.println("Returning temp");
        return tmp;
    }

    //make IPC request to Book service to show that book has been returned
    public Loan returnLoan(int id){
        Loan loanToReturn = repository.findById(id).orElse(null);
            loanToReturn.setReturnDate(java.sql.Date.valueOf(LocalDate.now()));
            loanToReturn.setCheckedOut(false);
            loanToReturn.setReturned(true);
            //IPC BELOW: set Book/Item Availability to True
           // template.getForObject("http://" + loanToReturn.getItemType().toUpperCase() + "-SERVICE/"+loanToReturn.getItemType().toLowerCase()+"/update" + loanToReturn.getItemType().toLowerCase() + "Availability" + "/" + loanToReturn.getItemID() + "/" + true, String.class);
        flagItemAvailability(loanToReturn.getItemType(), loanToReturn.getItemID(), true);
            return repository.save(loanToReturn);
    }

    //Possible Async extension with message broker: Method for flagging return date notification


    //these methods make calls to the repository/persistence layer
    public List<Loan> getLoans(){
        return repository.findAll();
    }

    public Loan getLoanById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Loan> getLoansByOwner(int ownerId) {
        return repository.findAllByOwnerID(ownerId);
    }

    public List<Loan> getLoansByBorrower(int borrowerId) {
        return repository.findAllByBorrowerID(borrowerId);
    }

    public List<Loan> getLoansByItemID(int itemId) {
        return repository.findAllByItemID(itemId);
    }

    public List<Loan> getLoansByItemType(String itemType) {
        return repository.findAllByItemType(itemType);
    }


    public List<Loan> getLoansByCheckOutDate(Date checkOutDate) {
        return repository.findAllByCheckOutDate(checkOutDate);
    }

    public List<Loan> getLoansBeforeCheckOutDate(Date checkOutDate) {
        return repository.findAllByCheckOutDateBefore(checkOutDate);
    }

    public List<Loan> getLoansAfterCheckOutDate(Date checkOutDate) {
        return repository.findAllByCheckOutDateAfter(checkOutDate);
    }

    public List<Loan> getLoansByReturnDate(Date returnDate) {
        return repository.findAllByReturnDate(returnDate);
    }

    public List<Loan> getLoansBeforeReturnDate(Date returnDate) {
        return repository.findAllByReturnDateBefore(returnDate);
    }

    public List<Loan> getLoansAfterReturnDate(Date returnDate) {
        return repository.findAllByReturnDateAfter(returnDate);
    }

    public List<Loan> findReturnedLoans() {
        return repository.findByIsReturnedTrue();
    }

    public List<Loan> findnonReturnedLoans() {
        return repository.findByIsReturnedFalse();
    }

    public List<Loan> findCheckedOutLoans() {
        return repository.findByIsCheckedOutTrue();
    }

    public List<Loan> findnonCheckedOutLoans() {
        return repository.findByIsCheckedOutFalse();
    }


    //Loan cannot be deleted for auditing purposes
    /*
    public String deleteLoan(int id) {
        repository.deleteById(id);
        return "loan removed!" + id;
    }

     */


    //check if member exists, we cannot access the member DB directly by rather have to use API composition
    public boolean checkMemberIDValidity(int id){
        boolean memberValidity = false;
        //String existingMember = template.getForObject("http://MEMBER-SERVICE/member/memberById/"+id, String.class);
        String existingMember = template.getForObject("http://MEMBER-SERVICE:8282/member/memberById/"+id, String.class);
        if(existingMember != null){
            memberValidity = true;
        }
        return memberValidity;
    }

    //check item with ID to update exists as well as if the Item exists. We have to adhere to API contracts across all services for IPC to work
    public boolean checkItemValidityAndAvailability(String itemType, int itemID){
        boolean itemValidity = false;
        int portnum = getPortNumber(itemType);
        //check Item exists
        //String ItemQuery = template.getForObject("http://"+itemType.toUpperCase()+"-SERVICE"+"/"+itemType.toLowerCase()+"/"+itemType.toLowerCase()+"ById"+"/"+itemID, String.class);
        //String ItemQuery = template.getForObject("http://"+itemType.toUpperCase()+"-SERVICE"+":8181/"+itemType.toLowerCase()+"/"+itemType.toLowerCase()+"ById"+"/"+itemID, String.class);
        String ItemQuery = template.getForObject("http://"+itemType.toUpperCase()+"-SERVICE"+ ":"+portnum+"/" +itemType.toLowerCase()+"/"+itemType.toLowerCase()+"ById"+"/"+itemID, String.class);
        if(ItemQuery != null){
            //check Item is Available
            //String itemAvailability =  template.getForObject("http://" + itemType.toUpperCase() + "-SERVICE"+"/"+itemType.toLowerCase()+"/check" + itemType.toLowerCase() + "Availability" + "/" + itemID, String.class);
            //String itemAvailability =  template.getForObject("http://" + itemType.toUpperCase() + "-SERVICE"+":8181/"+itemType.toLowerCase()+"/check" + itemType.toLowerCase() + "Availability" + "/" + itemID, String.class);
            String itemAvailability =  template.getForObject("http://" + itemType.toUpperCase() + "-SERVICE"+ ":"+portnum+"/" +itemType.toLowerCase()+"/check" + itemType.toLowerCase() + "Availability" + "/" + itemID, String.class);
            if(itemAvailability != null) {
                itemValidity = true;
            }
        }

        return itemValidity;
    }

    //IPC method to find availability of an item
    public void flagItemAvailability(String itemType, int itemID, boolean itemAvailability){
        int portnum = getPortNumber(itemType);
        //template.getForObject("http://" + itemType.toUpperCase() + "-SERVICE/"+itemType.toLowerCase()+"/update" + itemType.toLowerCase() + "Availability" + "/" + itemID + "/" + itemAvailability, String.class);
        //template.getForObject("http://" + itemType.toUpperCase() + "-SERVICE:8181/"+itemType.toLowerCase()+"/update" + itemType.toLowerCase() + "Availability" + "/" + itemID + "/" + itemAvailability, String.class);
        template.getForObject("http://" + itemType.toUpperCase() + "-SERVICE"+ ":"+portnum+"/" +itemType.toLowerCase()+"/update" + itemType.toLowerCase() + "Availability" + "/" + itemID + "/" + itemAvailability, String.class);

    }

    //IPC method to find existence of an entity
    public String getEntityExistence(String item, int itemID){
        //String ItemQuery = template.getForObject("http://"+item.toUpperCase()+"-SERVICE"+"/"+item.toLowerCase()+"/"+item.toLowerCase()+"ById"+"/"+itemID, String.class);
        //return ItemQuery;
        //String ItemQuery = template.getForObject("http://"+item.toUpperCase()+"-SERVICE:8181"+"/"+item.toLowerCase()+"/"+item.toLowerCase()+"ById"+"/"+itemID, String.class);
        //return ItemQuery;
        int portnum = getPortNumber(item);
        String ItemQuery = template.getForObject("http://"+item.toUpperCase()+"-SERVICE"+ ":"+portnum+"/"+item.toLowerCase()+"/"+item.toLowerCase()+"ById"+"/"+itemID, String.class);
        return ItemQuery;
    }

    //IPC to check item Availability
    public String getItemAvailability(String item, int itemID){
        //String itemAvailability =  template.getForObject("http://" + item.toUpperCase() + "-SERVICE"+"/"+item.toLowerCase()+"/check" + item.toLowerCase() + "Availability" + "/" + itemID, String.class);
        //return itemAvailability;
        //String itemAvailability =  template.getForObject("http://" + item.toUpperCase() + "-SERVICE:8181"+"/"+item.toLowerCase()+"/check" + item.toLowerCase() + "Availability" + "/" + itemID, String.class);
        //return itemAvailability;
        int portnum = getPortNumber(item);
        String itemAvailability =  template.getForObject("http://" + item.toUpperCase() + "-SERVICE"+ ":"+portnum+"/"+item.toLowerCase()+"/check" + item.toLowerCase() + "Availability" + "/" + itemID, String.class);
        return itemAvailability;
    }

    //IPC to check ownership
    public String getOwnership(String item, int ownerID){
        //String ownership = template.getForObject("http://" + item.toUpperCase() + "-SERVICE" + "/" +item.toLowerCase()+ "/getOwnerId" + "/" + ownerID, String.class);
        //return ownership;
        //String ownership = template.getForObject("http://" + item.toUpperCase() + "-SERVICE:8181" + "/" +item.toLowerCase()+ "/getOwnerId" + "/" + ownerID, String.class);
        //return ownership;
        int portnum = getPortNumber(item);
        String ownership = template.getForObject("http://" + item.toUpperCase() + "-SERVICE"+ ":"+portnum+"/" +item.toLowerCase()+ "/getOwnerId" + "/" + ownerID, String.class);
        return ownership;
    }

    public int getPortNumber(String item){

        int portnum = 0;
        if(item.equalsIgnoreCase("member")){portnum = 8282;}
        if(item.equalsIgnoreCase("book")){portnum = 8181;}
        if(item.equalsIgnoreCase("loan")){portnum = 8383;}
        return portnum;
    }


}
