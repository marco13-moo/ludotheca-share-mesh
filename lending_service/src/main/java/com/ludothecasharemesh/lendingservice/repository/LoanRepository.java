package com.ludothecasharemesh.lendingservice.repository;


import com.ludothecasharemesh.lendingservice.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.*;


public interface LoanRepository extends JpaRepository<Loan, Integer>{

    //More about JPA can be found here: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference

    List<Loan> findAllByOwnerID(int ownerId);

    List<Loan> findAllByBorrowerID(int borrowerId);

    List<Loan> findAllByItemID(int itemID);

    List<Loan> findAllByItemType(String itemType);

    List<Loan> findAllByCheckOutDate(@Param("checkOutDate") Date checkOutDate); //https://www.baeldung.com/spring-data-jpa-query-by-date

    List<Loan> findAllByCheckOutDateBefore(@Param("checkOutDate") Date checkOutDate);

    List<Loan> findAllByCheckOutDateAfter(@Param("checkOutDate") Date checkOutDate);

    List<Loan> findAllByReturnDate(@Param("returnDate") Date returnDate);

    List<Loan> findAllByReturnDateBefore(@Param("returnDate") Date returnDate);

    List<Loan> findAllByReturnDateAfter(@Param("returnDate") Date returnDate);

    List<Loan> findByIsReturnedTrue();

    List<Loan> findByIsReturnedFalse();

    List<Loan> findByIsCheckedOutTrue();

    List<Loan> findByIsCheckedOutFalse();


}
