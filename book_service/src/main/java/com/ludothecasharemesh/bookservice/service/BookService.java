package com.ludothecasharemesh.bookservice.service;

import com.ludothecasharemesh.bookservice.entity.Book;
import com.ludothecasharemesh.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Repository
@Service
public class BookService {

   @Autowired //see lending or member service classes on a note about autowired
    private BookRepository repository;

    @Autowired
    private RestTemplate template;


    public Book addBook(Book book){

        Book temp = new Book();
        int memberID = book.getOwnerID();
        //here is inter-service communication. check if member exists
        //String result = template.getForObject("http://MEMBER-SERVICE/member/memberById/"+memberID, String.class);
        String result = template.getForObject("http://MEMBER-SERVICE:8282/member/memberById/"+memberID, String.class);
        System.out.println(result);
        if(result != null){
            book.setAvailable(true);
            temp = repository.save(book);
            System.out.println("added book, member does exist");
        }else{
            System.out.println("Cannot add book, member does not exist");
            temp = null;
            }
        return temp;

    }

    public void updateBookAvailability(int id, boolean avail){

        if(avail){repository.setBookAvailTrue(id);
        System.out.println("Avail status:"+avail);
        }

        if(!avail){repository.setBookAvailFalse(id);
            System.out.println("Avail status:"+avail);
        }
    }


    public boolean checkBookAvailability(int id){

        Book b = getBookById(id);
        return b.isAvailable();
    }

    //see whos the owner of a specific book
    public int getBookOwnership(int id){
        Book b = getBookById(id);
        return b.getOwnerID();
    }


    public List<Book> getBooks(){
        return repository.findAll();
    }

    public Book getBookById(int id) {
        return repository.findById(id).orElse(null);
    }

    public List<Book> getBooksByTitle(String title) {
        return repository.findAllByTitle(title);
    }

    public List<Book> getBooksByAuthor(String author) {

        return repository.findAllByAuthor(author);
    }

    public List<Book> getBooksByPublisher(String publisher) {

        return repository.findAllByPublisher(publisher);
    }

    public List<Book> getBooksByLanguage(String language) {

        return repository.findAllByLanguage(language);
    }

    public List<Book> getBooksByYear(int year) {

        return repository.findAllByYear(year);
    }

    public List<Book> showOwnerBookCollection(int ownerId) {

        return repository.findAllByOwnerID(ownerId);
    }

    public List<Book> getBooksByTopic(String topic) {

        return repository.findAllByTopic(topic);
    }

    public List<Book> getBooksByGenre(String genre) {

        return repository.findAllByGenre(genre);
    }

    public void deleteBook(int id) {
        Book bookToDelete = getBookById(id);
        if(bookToDelete.isAvailable()){
            repository.deleteById(id);
        }else{
            System.out.println("Cannot delete book, book is currently on loan");
        }
    }


    /*
    * For this library design, you cannot update the ownerID as it
    * has implications in the lending service, so the book will have to be deleted if owners change
    * You cannot change the available status of the book, this is only done via the loan system
    * */
    public void updateBook(Book book){
        Book existingBook = repository.findById(book.getId()).orElse(null);
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPublisher(book.getPublisher());
            existingBook.setLanguage(book.getLanguage());
            existingBook.setYear(book.getYear());
            existingBook.setGenre(book.getGenre());
            existingBook.setTopic(book.getTopic());
            System.out.println("Book updated!");
            repository.save(existingBook);
    }


    public List<Book> findTrueBookAvailability() {
            return repository.findByIsAvailableTrue();
    }

    public List<Book> findFalseBookAvailability() {
        return repository.findByIsAvailableFalse();
    }

    //IPC to check ownership
    public String getMembership(String item, int ownerID){
        //String ownership = template.getForObject("http://" + item.toUpperCase() + "-SERVICE" + "/" +item.toLowerCase()+ "/getOwnerId" + "/" + ownerID, String.class);
        int portnum = 0;
        if(item.equalsIgnoreCase("member")){portnum = 8282;}
        if(item.equalsIgnoreCase("book")){portnum = 8181;}
        if(item.equalsIgnoreCase("loan")){portnum = 8383;}
        String ownership = template.getForObject("http://" + item.toUpperCase() + "-SERVICE" + ":"+portnum+"/" +item.toLowerCase()+ "/getOwnerId" + "/" + ownerID, String.class);
        return ownership;
    }

}
