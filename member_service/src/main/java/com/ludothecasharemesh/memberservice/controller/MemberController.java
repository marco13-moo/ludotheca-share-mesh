package com.ludothecasharemesh.memberservice.controller;


import com.ludothecasharemesh.memberservice.entity.Member;
import com.ludothecasharemesh.memberservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.http.HTTPException;
import java.util.List;

//See more about controllers at: https://www.baeldung.com/spring-controller-vs-restcontroller
@RestController //"Every request handling method of the controller class automatically serializes return objects into HttpResponse"
@RequestMapping("/member") //member noun has to come before final end-point text
public class MemberController {

    @Autowired
    private MemberService service; //instanciate new member service

    @GetMapping("")
    public void twohundred() {
        System.out.println("Gives 200 response");
    }

    @PostMapping("/addMember") //add a new member
    public Member addMember(@RequestBody Member member){ //takes in member object, in JSON
        return service.saveMember(member); //calls saveMember method in memberService
    }



    @GetMapping("/members") //list all members
    public List<Member> findAllMembers() {
        return service.getMembers(); //calls getMember method in memberService
    }

    @GetMapping("/memberById/{id}")
    public Member findMemberById(@PathVariable int id) {
        Member temp = null;
        try {
            temp = service.getMemberById(id);

        }catch(HTTPException e){
            e.printStackTrace();
        }
        return temp; //returns null if not id is found, HTTPException also gets caught
    }


    @GetMapping("/name/{name}") //get member by name
    public List<Member> findMembersByName(@PathVariable String name) {
        return service.getMembersByName(name);
    }


    @GetMapping("/surname/{surname}") //get member by surname
    public List<Member> findMembersBySurname(@PathVariable String surname) {
        return service.getMembersBySurname(surname);
    }

    @GetMapping("/email/{email}")  //get member by email
    public List<Member> findMembersByEmail(@PathVariable String email) {
        return service.getMembersByEmail(email);
    }


    @PutMapping("/update") //update member
    public Member updateMember(@RequestBody Member member) { //takes in a member object from JSON

        return service.updateMember(member);
    }

    @GetMapping("/checkNoLoansOnMember/{id}") //check there is no loans on the member
    public boolean checkNoLoansOnMember(@PathVariable int id) {

        return service.checkNoLoansOnMember(id); //this calls a method that has inter-process communication
    }

    @DeleteMapping("/deleteById/{id}") //delete a member by id
    public void deleteMember(@PathVariable int id) {

        service.deleteMember(id);
    }



}
