package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc // MockMvc 위한 자동설정
@AutoConfigureTestDatabase
class HealthCheckControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void 헬스_체크_응답이_200으로_온다() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/health_check.html"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}