package com.ludothecasharemesh.bookservice.controller;

import com.ludothecasharemesh.bookservice.entity.Book;
import com.ludothecasharemesh.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//See more about controllers at: https://www.baeldung.com/spring-controller-vs-restcontroller
@RestController //"Every request handling method of the controller class automatically serializes return objects into HttpResponse"
@RequestMapping("/book") //book noun has to come before final end-point text
public class BookController {

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
    private BookService service;

    @GetMapping("")
    public void twohundred() {
        System.out.println("Gives 200 response");
    }

    @PostMapping("/addBook") //add book
    public Book addBook(@RequestBody Book book){

        Book tmp =  service.addBook(book);
        if(tmp == null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");}
        return tmp;

    }

    @GetMapping("/books") //show all books
    public List<Book> findAllBooks() {
        return service.getBooks();
    }

    @GetMapping("/bookById/{id}") //get book by ID
    public Book findBookById(@PathVariable int id) {
        return service.getBookById(id);
    }


    @GetMapping("/title/{title}") //get books by a specific title
    public List<Book> findBooksByTitle(@PathVariable String title) {
        return service.getBooksByTitle(title);
    }


    @GetMapping("/author/{author}") //get books by specific author
    public List<Book> findBooksByAuthor(@PathVariable String author) {

        return service.getBooksByAuthor(author);
    }

    @GetMapping("/publisher/{publisher}") //get books by a specific publisher
    public List<Book> findBooksByPublisher(@PathVariable String publisher) {

        return service.getBooksByPublisher(publisher);
    }


    @GetMapping("/language/{language}") //get books by a specific language
    public List<Book> findBooksByLanguage(@PathVariable String language) {

        return service.getBooksByLanguage(language);
    }


    @GetMapping("/year/{year}") //get books by a specific year
    public List<Book> findBooksByYear(@PathVariable int year) {

        return service.getBooksByYear(year);
    }


    @GetMapping("/topic/{topic}") //get books by a specific topic
    public List<Book> findBooksByTopic(@PathVariable String topic) {

        return service.getBooksByTopic(topic);
    }


    @GetMapping("/genre/{genre}") //get books by a specific genre
    public List<Book> findBooksByGenre(@PathVariable String genre) {

        return service.getBooksByGenre(genre);
    }


    @GetMapping("/ownerCollection/{ownerId}") //get all books from a specific owner
    public List<Book> showOwnerBookCollection(@PathVariable int ownerId) {
        return service.showOwnerBookCollection(ownerId);
        //show HTTP error if it is null
    }


    @PutMapping("/update") //update a book
    public void updateBook(@RequestBody Book book) {
        service.updateBook(book);
    }


    @GetMapping("/updatebookAvailability/{id}/{availBool}") //update book availability
    public void updateBookAvailability(@PathVariable int id, @PathVariable("availBool")boolean avail){
        service.updateBookAvailability(id, avail);
    }


    @GetMapping("/checkbookAvailability/{id}") //check book availability by id
    public boolean checkBookAvailability(@PathVariable int id){
        return service.checkBookAvailability(id); //maybe do string conversion?
    }


    @GetMapping("/getOwnerId/{id}")
    public int getBookOwnership(@PathVariable int id){
        return service.getBookOwnership(id);
    }


    @DeleteMapping("/deleteById/{id}") //delete book with specific ID. lack of Auth means we cant allow ownerOnly deletion
    public void deleteBook(@PathVariable int id) {
        service.deleteBook(id);
    }



    @GetMapping("/bookAvailability/true") //check to see if book is available
    public List<Book> findTrueBookAvailability() {
        return service.findTrueBookAvailability(); //https://stackoverflow.com/questions/31223754/optional-int-parameter-page-is-present-but-cannot-be-translated-into-a-null-va
    }


    @GetMapping("/bookAvailability/false")  //check to see if book is unavailable
    public List<Book> findFalseBookAvailability() {
        return service.findFalseBookAvailability(); //https://stackoverflow.com/questions/31223754/optional-int-parameter-page-is-present-but-cannot-be-translated-into-a-null-va
    }
    
}

//https://softwareengineering.stackexchange.com/questions/337274/what-are-repositories-services-and-actions-controllers
//The controller objects works as a gateway between your input and the domain logic, is decides what to do with the input and
// how to output the response.
