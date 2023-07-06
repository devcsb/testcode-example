package com.example.demo.user.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class MyProfileResponseTest {
    @Test
    void User로_유저프로필_정보를_생성할_수_있다() throws Exception {
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
        MyProfileResponse myProfileResponse = MyProfileResponse.from(user);
        //then
        assertThat(myProfileResponse.getId()).isEqualTo(1L);
        assertThat(myProfileResponse.getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(myProfileResponse.getNickname()).isEqualTo("tester");
        assertThat(myProfileResponse.getAddress()).isEqualTo("seoul");
        assertThat(myProfileResponse.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(myProfileResponse.getLastLoginAt()).isEqualTo(100L);
    }
}