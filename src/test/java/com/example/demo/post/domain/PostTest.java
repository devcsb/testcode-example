package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


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

        TestClockHolder clockHolder = new TestClockHolder(1678530679919L);

        //when
        Post post = Post.from(writer, postCreate, clockHolder);

        //then
        assertThat(post.getContent()).isEqualTo("PostContent");
        assertThat(post.getCreatedAt()).isEqualTo(1678530679919L);
        assertThat(post.getWriter().getEmail()).isEqualTo("devcsb119@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("tester");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa");
    }

    @Test
    void PostUpdate_객체로_게시글을_업데이트_할_수_있다() throws Exception {
        //given
        User writer = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .build();
        Post post = Post.builder()
                .content("OriginContent")
                .createdAt(1678530679919L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        PostUpdate postUpdate = PostUpdate.builder()
                .content("updatedContent")
                .build();

        TestClockHolder clockHolder = new TestClockHolder(1678999977779L);

        //when
         post = post.update(postUpdate, clockHolder);

        //then
        assertThat(post.getContent()).isEqualTo("updatedContent");
        assertThat(post.getCreatedAt()).isEqualTo(1678530679919L);
        assertThat(post.getModifiedAt()).isEqualTo(1678999977779L);
    }
}