package com.ludothecasharemesh.memberservice;

import com.ludothecasharemesh.memberservice.entity.Member;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//Source: https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm

public class MemberServiceControllerTest extends AbstractTest {


    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getMembersList() throws Exception {
        String uri = "/member/members";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Member[] memberlist = super.mapFromJson(content, Member[].class);
        assertTrue(memberlist.length > 0);
    }

    @Test
    public void createMember() throws Exception {
        //NB!!   //check for duplicate member
        String uri = "/member/addMember";
        Member member = new Member(8, "Billy", "Loomis", "floomis@woodsboro.edu");

        String inputJson = super.mapToJson(member);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //check how to do check on JSON response
    }

    //NB!!
    @Test
    public void updateMember() throws Exception {
        ////make IPC Request: NB!!Check that there is no loans out on them
        String uri = "/member/update";
        Member member = new Member(13, "Candice", "Loomis", "floomis@woodsboro.edu");

        String inputJson = super.mapToJson(member);
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
        String uri = "/member/delete/14";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //String content = mvcResult.getResponse().getContentAsString();
        //assertEquals(content, "Product is deleted successsfully");
    }

}
