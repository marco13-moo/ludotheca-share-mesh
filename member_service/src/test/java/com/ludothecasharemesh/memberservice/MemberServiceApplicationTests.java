package com.ludothecasharemesh.memberservice;

import com.ludothecasharemesh.memberservice.entity.Member;
import com.ludothecasharemesh.memberservice.repository.MemberRepository;
import com.ludothecasharemesh.memberservice.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


//@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureMockMvc
class MemberServiceApplicationTests {
    //https://rest-assured.io/
    //https://www.tutorialspoint.com/spring_boot/spring_boot_unit_test_cases.htm
    //https://phoenixnap.com/blog/microservices-continuous-testing
    //see page 320 and 324. Microservices Patterns - Richardson

    @Test
    void contextLoads() {
    }

    @Autowired
    private MemberService service;

    @MockBean
    private MemberRepository repository;



    @Test
    public void getMembersTest() {
        when(repository.findAll()).thenReturn(Stream
                .of(new Member(1, "name","surname", "email"), new Member(2, "name","surname", "email")).collect(Collectors.toList()));
        assertEquals(2, service.getMembers().size());
    }


    @Test
    public void saveMemberTest() {
        Member member = new Member(1, "name","surname", "email");
        when(repository.save(member)).thenReturn(member);
        assertEquals(member, service.saveMember(member));
    }


    @Test
    public void deleteUserTest() {
        Member member = new Member(4, "name","surname", "email");
        service.deleteMember(4);
    }




}
