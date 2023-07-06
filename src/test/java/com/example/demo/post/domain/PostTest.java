package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;


class PostTest {

    @Test
    void PostCreate_객체로_게시글을_만들_수_있다() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("PostContent")
                .build();

        User writer = User.builder()
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();

        //when
        Post post = Post.from(writer, postCreate);

        //then
        assertThat(post.getContent()).isEqualTo("PostContent");
        assertThat(post.getWriter().getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("tester");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");

    }

    @Test
    void 유저는_PostUpdate_객체로_게시글을_업데이트_할_수_있다() throws Exception { //TODO 수정
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("PostContent")
                .build();
        //when
        //then
    }

}