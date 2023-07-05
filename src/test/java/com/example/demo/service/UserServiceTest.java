package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")

@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/user-delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() throws Exception {
        //given
        String email = "devcsb119@gmail.com";

        //when
        UserEntity result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("tester1");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() throws Exception {
        //given
        String email = "devcsb1@gmail.com";
        //when
        //then
        assertThatThrownBy(() -> userService.getByEmail(email)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() throws Exception {
        //given
        //when
        UserEntity result = userService.getById(1);

        //then
        assertThat(result.getNickname()).isEqualTo("tester1");
    }
    
    @Test
    void getById는_PENDING_상태인_유저는_찾아올_수_없다() throws Exception {
        //given
        //when
        //then
        assertThatThrownBy(() -> userService.getById(2)).isInstanceOf(ResourceNotFoundException.class);

    }
}