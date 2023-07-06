package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostResponseTest {
    @Test
    void Post로_응답을_생성할_수_있다() throws Exception {
        //given
        Post post = Post.builder()
                .content("PostContent")
                .writer(User.builder()
                        .id(1L)
                        .email("devcsb119@gmail.com")
                        .nickname("tester")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                        .build())
                .build();

        //when
        PostResponse postResponse = PostResponse.from(post);

        //then
        assertThat(post.getContent()).isEqualTo("PostContent");
        assertThat(post.getWriter().getId()).isEqualTo(1L);
        assertThat(post.getWriter().getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("tester");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}