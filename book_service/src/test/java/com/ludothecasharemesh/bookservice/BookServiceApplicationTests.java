package com.ludothecasharemesh.bookservice;

import com.ludothecasharemesh.bookservice.entity.Book;
import com.ludothecasharemesh.bookservice.repository.BookRepository;
import com.ludothecasharemesh.bookservice.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private BookService service;

    @MockBean
    private BookRepository repository;

    @Test
    public void getBooksTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(new Book(1, "A", "A", "A", "A", 1997, "T", "T", 1, false), new Book(2, "A", "A", "A", "A", 1997, "T", "T", 1, false)).collect(Collectors.toList()));
        assertEquals(2, service.getBooks().size());
    }


    @Test
    public void addBookTest() {
        Book book = new Book(1, "A", "A", "A", "A", 1997, "T", "T", 2, false);
        when(repository.save(book)).thenReturn(book);
        assertEquals(book, service.addBook(book));
    }


    @Test
    public void deleteBookTest() {
        Book book = new Book(40, "A", "A", "A", "A", 1997, "T", "T", 3, true);
        repository.save(book);
        service.deleteBook(40);
    }





}
