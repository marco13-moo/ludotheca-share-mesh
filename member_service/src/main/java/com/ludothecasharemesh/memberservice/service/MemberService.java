package com.ludothecasharemesh.memberservice.service;


import com.ludothecasharemesh.memberservice.entity.Member;
import com.ludothecasharemesh.memberservice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;

    @Autowired
    private RestTemplate template;

    public Member saveMember(Member member){
        return repository.save(member);
    }

    public Member getMember(Member member){return repository.save(member);}

    public List<Member> getMembers(){
        return repository.findAll();
    }

    public Member getMemberById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Member> getMembersByName(String name){
        return repository.findAllByName(name);
    }

    public List<Member> getMembersBySurname(String surname){
        return repository.findAllBySurname(surname);
    }

    public List<Member> getMembersByEmail(String email){
        return repository.findAllByEmail(email);
    }


    public void deleteMember(int id) {

        boolean noLoan = checkNoLoansOnMember(id);
        if(noLoan) {
            repository.deleteById(id);
            System.out.println("Successfully deleted");
        }else{
            System.out.println("Cannot delete member");
        }
    }

    //no need for an IPC request here as you cannot change the ID
    public Member updateMember(Member member) {
        Member existingMember = repository.findById(member.getId()).orElse(null);
            existingMember.setName(member.getName());
            existingMember.setSurname(member.getSurname());
            existingMember.setEmail(member.getEmail());
        return repository.save(existingMember);
    }

    /*
    Method for making sure there is no loan on their name
    get list of ownerLoans that matches id
    get list of borrowerLoans that matches id
    set noLoans bool to false;
    parse JSON to string, if returned == true, then flag noloans to true
    * */

    //Method for making sure there is no loan on their name
    public boolean checkNoLoansOnMember(int id){
        //https://stackoverflow.com/questions/23674046/get-list-of-json-objects-with-spring-resttemplate

        boolean noLoan = false;
        //get list of ownerLoans that matches id
        //String noLoanBorrower = template.getForObject("http://LENDING-SERVICE/loan/loanByBorrowerId/"+id, String.class);
        //String noLoanOwner = template.getForObject("http://LENDING-SERVICE/loan/loanByOwnerId/"+id, String.class);
        String noLoanBorrower = template.getForObject("http://LENDING-SERVICE:8383/loan/loanByBorrowerId/"+id, String.class);
        String noLoanOwner = template.getForObject("http://LENDING-SERVICE:8383/loan/loanByOwnerId/"+id, String.class);

        System.out.println(noLoanBorrower);
        System.out.println(noLoanOwner);
        if(noLoanBorrower.equals("[]") && noLoanOwner.equals("[]")){
            System.out.println("no loans outstanding as JSON is empty");
            noLoan = true;
        }else{
            //check that those loans have returned value of false, if not false in the list, make noLoan = true
            boolean isReturned = checkForNoReturn(noLoanBorrower, noLoanOwner);
            /*put if statement here and change the noloan boolean*/
            if(isReturned){
                noLoan = true;
                System.out.println("no loans outstanding as all loans have been returned");
            }else{
            System.out.println("loan outstanding");
            }
        }

        return noLoan;
    }


    public boolean checkForNoReturn(String noLoanBorrower, String noLoanOwner){

        boolean b = false;
        if(noLoanBorrower.contains("\"returned\":false")){
            System.out.println("FLAG!! noLoanBorrower");
            b = true;
        }

        if(noLoanOwner.contains("\"returned\":false")){
            System.out.println("FLAG!! noLoanOwner");
            b = true;
        }

        return b;
    }

}
