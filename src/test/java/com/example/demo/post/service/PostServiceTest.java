package com.example.demo.post.service;

import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    private PostService postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.postService = PostService.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .clockHolder(new TestClockHolder(1678530679919L))
                .build();
        User user1 = User.builder()
                .id(1L)
                .email("devcsb119@gmail.com")
                .nickname("tester")
                .address("seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        User user2 = User.builder()
                .id(2L)
                .email("'devcsb1@gmail.com'")
                .nickname("tester1")
                .address("seoul")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaab")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build();
        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
        fakePostRepository.save(Post.builder()
                .id(1L)
                .content("helloTest")
                .createdAt(1678530679919L)
                .modifiedAt(0L)
                .writer(user1)
                .build());
    }

    @Test
    void 게시글_id로_게시글을_가져올_수_있다() throws Exception {
        //given
        //when
        Post result = postService.getPostById(1);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("helloTest");
        assertThat(result.getWriter().getId()).isEqualTo(1);
        assertThat(result.getCreatedAt()).isEqualTo(1678530679919L);
    }

    @Test
    void postCreate로_게시글을_생성할_수_있다() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("someContent")
                .build();

        //when
        Post result = postService.create(postCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("someContent");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
        assertThat(result.getCreatedAt()).isEqualTo(1678530679919L);
    }

    @Test
    void postUpdateDto로_게시글을_업데이트할_수_있다() throws Exception {
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("modifiedContent")
                .build();
        //when
        Post post = postService.update(1, postUpdate);

        //then
        assertThat(post.getContent()).isEqualTo("modifiedContent");
        assertThat(post.getModifiedAt()).isGreaterThan(0);
    }
}