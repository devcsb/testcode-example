package com.example.demo.post.controller;

import com.example.demo.post.domain.PostUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class PostControllerTest {

    @Test
    void 사용자는_게시글_id로_게시물을_단건_조회할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("helloTest"))
                .andExpect(jsonPath("$.createdAt").value(1678530679919L))
                .andExpect(jsonPath("$.modifiedAt").value(0L))
                .andExpect(jsonPath("$.writer.id").value(1L))
                .andExpect(jsonPath("$.writer.email").value("devcsb119@gmail.com"))
                .andExpect(jsonPath("$.writer.nickname").value("tester"))
                .andExpect(jsonPath("$.writer.status").value("ACTIVE"))
                .andExpect(jsonPath("$.writer.lastLoginAt").value(0L));
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/posts/500"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Posts에서 ID 500를 찾을 수 없습니다."));
    }

    @Test
    void 사용자는_게시글_수정을_할_수_있다() throws Exception {
        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("modifiedPostContent")
                .build();
        //when
        //then
        mockMvc.perform(put("/api/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.content").value("modifiedPostContent"))
                .andExpect(jsonPath("$.createdAt").value(1678530679919L))
                .andExpect(jsonPath("$.modifiedAt").isNumber()) //FIXME
                .andExpect(jsonPath("$.writer.id").value(1L))
                .andExpect(jsonPath("$.writer.email").value("devcsb119@gmail.com"))
                .andExpect(jsonPath("$.writer.nickname").value("tester"))
                .andExpect(jsonPath("$.writer.status").value("ACTIVE"))
                .andExpect(jsonPath("$.writer.lastLoginAt").value(0L));
    }
}