package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void User는_UserCreate_객체로_생성할_수_있다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .build();
        //when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa"));
        //then
        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getEmail()).isEqualTo("devcsb119@gmail.com");
        Assertions.assertThat(user.getNickname()).isEqualTo("tester");
        Assertions.assertThat(user.getAddress()).isEqualTo("seoul");
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        Assertions.assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");
    }

    @Test
    void User는_UserUpdate_객체로_데이터를_업데이트_할_수_있다() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("updatedNickName")
                .address("busan")
                .build();
        //when
        user = user.update(userUpdate);

        //then

        Assertions.assertThat(user.getId()).isEqualTo(1L);
        Assertions.assertThat(user.getEmail()).isEqualTo("devcsb119@gmail.com");
        Assertions.assertThat(user.getNickname()).isEqualTo("updatedNickName");
        Assertions.assertThat(user.getAddress()).isEqualTo("busan");
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        Assertions.assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");
        Assertions.assertThat(user.getLastLoginAt()).isEqualTo(100L);

    }

    @Test
    void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();

        //when
        user = user.login(new TestClockHolder(1693843933L));

        //then
        Assertions.assertThat(user.getLastLoginAt()).isEqualTo(1693843933L);
    }

    @Test
    void User는_유효한_인증_코드로_계정을_활성화_할_수_있다() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();

        //when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");

        //then
        Assertions.assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);

    }

    @Test
    void User는_잘못된_인증_코드로_계정을_활성화_하려하면_에러를_던진다() throws Exception {
        //given
        User user = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();

        //when
        //then
        Assertions.assertThatThrownBy(() -> user.certificate("zzzzz-aaaa-aaaa-aaaa-aaaaaaaaaaa"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }

}
