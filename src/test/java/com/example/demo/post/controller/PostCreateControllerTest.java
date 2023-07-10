package com.example.demo.post.controller;

import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


class PostCreateControllerTest {

    @Test
    void 사용자는_게시물을_작성할_수_있다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530679919L)
                .build();
        User user = User.builder()
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();
        testContainer.userRepository.save(user);

        PostCreate postCreate = PostCreate.builder()
                .writerId(1L)
                .content("PostContent")
                .build();

        //when
        ResponseEntity<PostResponse> result = testContainer.postCreateController.createPost(postCreate);

        //then
        Assertions.assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getContent()).isEqualTo("PostContent");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(1678530679919L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}