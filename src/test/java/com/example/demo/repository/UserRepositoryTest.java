package com.example.demo.repository;

import com.example.demo.model.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//@ExtendWith(SpringExtension.class) // @DataJpaTest에 내장되어있음.
@TestPropertySource("classpath:application-test.properties") // 기본 설정을 따르어도 문제 없음.
@DataJpaTest(showSql = true)
@Sql("/sql/user-repository-test-data.sql")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_유저_데이터를_찾아올_수_있다() {
        //given
        //when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() throws Exception {
        //given
        //when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_로_유저_데이터를_찾아올_수_있다() {
        //given
        //when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("devcsb119@gmail.com", UserStatus.ACTIVE);

        //then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() throws Exception {
        //given
        //when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("devcsb119@gmail.com", UserStatus.PENDING);

        //then
        assertThat(result.isEmpty()).isTrue();
    }


}
