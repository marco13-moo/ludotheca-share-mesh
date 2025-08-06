package com.ludothecasharemesh.memberservice.repository;

import com.ludothecasharemesh.memberservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//"Repository—An object that provides access to persistent entities and encapsulates the mechanism for accessing the database" C.Richardson Page 150 Microservices Patterns
public interface MemberRepository extends JpaRepository<Member, Integer>{

    //https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/jpa.repositories.html
    List<Member> findAllByName(String name);

    List<Member> findAllBySurname(String surname);

    List<Member> findAllByEmail(String email);
}

