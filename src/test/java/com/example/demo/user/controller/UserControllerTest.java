package com.example.demo.user.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.controller.port.UserReadService;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UserControllerTest {

    @Test
    void 사용자는_개인정보인_주소정보를_제외한_특정_유저의_정보를_전달받을_수_있다() throws Exception {
        //given
        UserController userController = UserController.builder()
                .userReadService(new UserReadService() {
                    @Override
                    public User getByEmail(String email) {
                        return null;
                    }

                    @Override
                    public User getById(long id) {
                        return User.builder()
                                .email("devcsb119@gmail.com")
                                .nickname("tester")
                                .address("seoul")
                                .status(UserStatus.ACTIVE)
                                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                                .build();
                    }
                })
                .build();
        //when
        ResponseEntity<UserResponse> result = userController.getUserById(1);

        //then
        assertThat(result.getBody().getId()).isNotNull();
        assertThat(result.getBody().getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(result.getBody().getNickname()).isEqualTo("tester");
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void 사용자는_존재하지않는_아이디로_api를_호출할_경우_404_응답을_받는다() throws Exception {
        //given
        UserController userController = UserController.builder()
                .userReadService(new UserReadService() {
                    @Override
                    public User getByEmail(String email) {
                        return null;
                    }

                    @Override
                    public User getById(long id) {
                        throw new ResourceNotFoundException("Users", id);
                    }
                })
                .build();
        //when
        //then
        assertThatThrownBy(() -> userController.getUserById(32434)).isInstanceOf(ResourceNotFoundException.class); //TODO 중괄호 체크

    }

    @Test
    void 사용자는_인증코드로_계정을_활성화시킬_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/2/verify")
                        .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab"))
                .andExpect(status().isFound())
                .andExpect(header().string("location", "http://localhost:3000"));
        UserEntity userEntity = userJpaRepository.findById(2L).get();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한없을_에러를_내려준다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/2/verify")
                        .queryParam("certificationCode", "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab"))
                .andExpect(status().isFound())
                .andExpect(header().string("location", "http://localhost:3000"));
        UserEntity userEntity = userJpaRepository.findById(2L).get();
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자는_인증_코드가_일치하지_않을_경우_권한없음_에러를_내려준다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/2/verify")
                        .queryParam("certificationCode", "zzzzzzz-aaaa-aaaa-aaaa-aaaaaaaaaab"))
                .andExpect(status().isForbidden());
    }

    @Test
    void 사용자는_내_정보를_불러올_때_개인정보인_주소도_가져올_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/users/me")
                        .header("EMAIL", "devcsb119@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("devcsb119@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("tester"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.address").value("seoul"));
    }

    @Test
    void 사용자는_내정보를_수정할_수_있다() throws Exception {
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("modifiedTester")
                .address("busan")
                .build();
        //when
        //then
        mockMvc.perform(put("/api/users/me")
                        .header("EMAIL", "devcsb119@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("devcsb119@gmail.com"))
                .andExpect(jsonPath("$.nickname").value("modifiedTester"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
                .andExpect(jsonPath("$.address").value("busan"));
    }
}