package com.ludothecasharemesh.lendingservice;

import com.ludothecasharemesh.lendingservice.entity.Loan;
import com.ludothecasharemesh.lendingservice.repository.LoanRepository;
import com.ludothecasharemesh.lendingservice.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class LoanServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private LoanService service;

    @MockBean
    private LoanRepository repository;

    @Test
    public void getLoansTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(new Loan(), new Loan()).collect(Collectors.toList()));
        assertEquals(2, service.getLoans().size());
    }


    @Test
    public void saveLoanTest() {
        Loan loan = new Loan();
        when(repository.save(loan)).thenReturn(loan);
        assertEquals(loan, service.checkOutLoan(loan));
    }


    /*
    @Test
    public void deleteLoanTest() {
        Loan loan = new Loan(24, 2, 3,39, "Book", java.sql.Date.valueOf(LocalDate.now()), java.sql.Date.valueOf(LocalDate.now().plusDays(14)), false, true);
        service.deleteLoan(4);
    }

     */

}
