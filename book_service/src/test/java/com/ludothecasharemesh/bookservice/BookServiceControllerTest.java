package com.ludothecasharemesh.bookservice;

import com.ludothecasharemesh.bookservice.entity.Book;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BookServiceControllerTest extends AbstractTest  {



    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void getBooksList() throws Exception {
        String uri = "/book/books";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        Book[] booklist = super.mapFromJson(content, Book[].class);
        assertTrue(booklist.length > 0);
    }

    @Test
    public void createBook() throws Exception {
        //NB!!   //check for duplicate member
        String uri = "/book/addBook";
        Book book = new Book(38, "A", "A", "A", "A", 1997, "T", "T", 3, true);

        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        //check how to do check on JSON response
    }

    //NB!!
    @Test
    public void updateBook() throws Exception {

        String uri = "/book/update";
        Book book = new Book(38, "A", "A", "A", "A", 1997, "T", "T", 3, true);

        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //String content = mvcResult.getResponse().getContentAsString();
        //assertEquals(content, "Product is updated successsfully");
    }

    @Test
    public void deleteBook() throws Exception {
        //make an IPC request to make sure there is no loan on their name
        String uri = "/book/deleteById/38";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        //String content = mvcResult.getResponse().getContentAsString();
        //assertEquals(content, "Product is deleted successsfully");
    }
}
