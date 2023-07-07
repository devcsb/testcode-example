package com.example.demo.user.controller;

import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("test@gmail.com")
                .nickname("jonedoe")
                .address("anywhere")
                .build();

        BDDMockito.doNothing().when(javaMailSender).send(ArgumentMatchers.any(SimpleMailMessage.class));
        //when
        //then
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreate)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("jonedoe"))
                .andExpect(jsonPath("$.status").value(UserStatus.PENDING.toString()));
    }
}