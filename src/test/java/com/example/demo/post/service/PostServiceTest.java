package com.example.demo.post.service;

import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
@SqlGroup({@Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @Test
    void 게시글_id로_게시글을_가져올_수_있다() throws Exception {
        //given
        //when
        Post result = postService.getPostById(1);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("helloTest");
        assertThat(result.getWriter().getId()).isEqualTo(1);
    }

    @Test
    void postCreate로_게시글을_생성할_수_있다() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("someContent")
                .build();

        //when
        Post post = postService.create(postCreate);

        //then
        Long writerId = post.getWriter().getId();
        User user = userService.getById(writerId);
        assertThat(user.getNickname()).isEqualTo(post.getWriter().getNickname());
        assertThat(user.getLastLoginAt()).isEqualTo(post.getWriter().getLastLoginAt());

        assertThat(writerId).isEqualTo(1);
        assertThat(post.getContent()).isEqualTo("someContent");
        assertThat(post.getCreatedAt()).isGreaterThan(0);
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