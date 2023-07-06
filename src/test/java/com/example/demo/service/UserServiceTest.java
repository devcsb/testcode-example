package com.example.demo.service;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;


@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() throws Exception {
        //given
        String email = "devcsb119@gmail.com";

        //when
        UserEntity result = userService.getByEmail(email);

        //then
        assertThat(result.getNickname()).isEqualTo("tester");
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
        assertThat(result.getNickname()).isEqualTo("tester");
    }

    @Test
    void getById는_PENDING_상태인_유저는_찾아올_수_없다() throws Exception {
        //given
        //when
        //then
        assertThatThrownBy(() -> userService.getById(2)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreate_를_이용하여_유저를_생성할_수_있다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("devcsb2@gmail")
                .address("seoul")
                .nickname("tester3")
                .build();
        //javaMailsender 인스턴스의 send 메소드 호출시, SimpleMailMessage를 파라미터로 넘기고, 아무 동작도 하지않는다는 뜻
        BDDMockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        //when
        UserEntity result = userService.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() throws Exception {
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("busan")
                .nickname("updater")
                .build();

        //when
        userService.update(1, userUpdate);

        //then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getId()).isEqualTo(1);
        assertThat(userEntity.getAddress()).isEqualTo("busan");
        assertThat(userEntity.getNickname()).isEqualTo("updater");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() throws Exception {
        //given
        //when
        userService.login(1);

        //then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getLastLoginAt()).isGreaterThan(0); //FIXME
    }

    @Test
    void PENDING_상태의_사용자는_인증코드로_활성화시킬_수_있다() throws Exception {
        //given
        //when
        userService.verifyEmail(2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab");

        //then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void ACTIVE_상태의_사용자도_인증코드만_일치하다면_정상수행된다() throws Exception {
        //given
        //when
        userService.verifyEmail(1, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");

        //then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 인증코드가_불일치할시_유저의_인증상태_변경에_실패한다() throws Exception {
        //given
        //when
        //then
        assertThatThrownBy(() -> userService.verifyEmail(1, "failaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}