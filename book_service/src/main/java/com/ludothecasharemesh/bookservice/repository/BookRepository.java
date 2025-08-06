package com.ludothecasharemesh.bookservice.repository;

import com.ludothecasharemesh.bookservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
Why no class you may ask?
* In most enterprise projects, you only need to define the repository interfaces. Spring Data JPA and Apache DeltaSpike Data can generate standard repository implementations for you.
*https://thorben-janssen.com/implementing-the-repository-pattern-with-jpa-and-hibernate/
* */

public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByTitle(String title);

    List<Book> findAllByAuthor(String author);

    List<Book> findAllByPublisher(String publisher);

    List<Book> findAllByLanguage(String language);

    List<Book> findAllByYear(int year);

    List<Book> findAllByTopic(String topic);

    List<Book> findAllByGenre(String genre);

    List<Book> findAllByOwnerID(int ownerId);

    List<Book> findByIsAvailableFalse();

    List<Book> findByIsAvailableTrue();

    @Modifying
    @Transactional
    @Query("UPDATE Book ba SET ba.isAvailable = true where ba.id=?1") //one can even create their own JPA queries as shown here
    void setBookAvailTrue(int x);

    @Modifying
    @Transactional
    @Query("UPDATE Book ba SET ba.isAvailable = false where ba.id=?1")
    void setBookAvailFalse(int x);


}


//Further notes on JPA if you wish to learn more:
//https://softwareengineering.stackexchange.com/questions/337274/what-are-repositories-services-and-actions-controllers
//The repository is a gateway between your domain/business layer and a data mapping layer, which is the layer that accesses the database and does the operations.
// Basically the repository is an abstraction to you database access.
//https://spring.io/blog/2011/02/10/getting-started-with-spring-data-jpa
//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
//https://docs.spring.io/spring-data/data-jpa/docs/1.0.0.M1/reference/html/#jpa.query-methods.query-creation