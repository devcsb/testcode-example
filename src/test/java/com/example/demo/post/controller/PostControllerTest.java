package com.example.demo.post.controller;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.TestContainer;
import com.example.demo.post.controller.response.PostResponse;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostControllerTest {

    @Test
    void 사용자는_게시글_id로_게시물을_단건_조회할_수_있다() {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530679919L)
                .build();
        User user = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("PostContent")
                .writer(user)
                .createdAt(100L)
                .build());

        //when
        ResponseEntity<PostResponse> result = testContainer.postController.getPostById(1);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("PostContent");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530679919L)
                .build();

        //when
        //then
        assertThatThrownBy(() -> testContainer.postController.getPostById(500)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자는_게시글_수정을_할_수_있다() throws Exception {
        //given
        TestContainer testContainer = TestContainer.builder()
                .clockHolder(() -> 1678530679919L)
                .build();
        User user = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .lastLoginAt(100L)
                .build();
        testContainer.userRepository.save(user);
        testContainer.postRepository.save(Post.builder()
                .id(1L)
                .content("PostContent")
                .writer(user)
                .createdAt(100L)
                .build());
        PostUpdate postUpdate = PostUpdate.builder()
                .content("updatedContent")
                .build();

        //when
        ResponseEntity<PostResponse> result = testContainer.postController.updatePost(1,postUpdate);

        //then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getContent()).isEqualTo("updatedContent");
        assertThat(result.getBody().getCreatedAt()).isEqualTo(100L);
        assertThat(result.getBody().getModifiedAt()).isEqualTo(1678530679919L);
        assertThat(result.getBody().getWriter().getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(result.getBody().getWriter().getNickname()).isEqualTo("tester");
        assertThat(result.getBody().getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}