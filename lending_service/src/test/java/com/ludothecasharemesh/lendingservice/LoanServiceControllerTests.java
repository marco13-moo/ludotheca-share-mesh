package com.ludothecasharemesh.lendingservice;

import com.ludothecasharemesh.lendingservice.entity.Loan;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoanServiceControllerTests extends AbstractTest {



    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getLoansList() throws Exception {
        String uri = "/loan/loans";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Loan[] loanlist = super.mapFromJson(content, Loan[].class);
        assertTrue(loanlist.length > 0);
    }

    @Test
    public void checkOutLoan() throws Exception {
        //NB!!   //check for duplicate member
        String uri = "/loan/checkOutLoan";
        Loan loan = new Loan();

        String inputJson = super.mapToJson(loan);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //check how to do check on JSON response
    }

    //NB!!
    @Test
    public void updateLoan() throws Exception {
        ////make IPC Request: NB!!Check that there is no loans out on them
        String uri = "/loan/update";
        Loan loan = new Loan();

        String inputJson = super.mapToJson(loan);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //String content = mvcResult.getResponse().getContentAsString();
        //assertEquals(content, "Product is updated successsfully");
    }

    @Test
    public void deleteMember() throws Exception {
        //make an IPC request to make sure there is no loan on their name
        String uri = "/loan/delete/19";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //String content = mvcResult.getResponse().getContentAsString();
        //assertEquals(content, "Product is deleted successsfully");
    }

}
