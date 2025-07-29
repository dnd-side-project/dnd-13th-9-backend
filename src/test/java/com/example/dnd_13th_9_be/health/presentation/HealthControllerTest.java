package com.example.dnd_13th_9_be.health.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;

@WebMvcTest(HealthController.class)
class HealthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("헬스체크 API는 status=UP 과 timestamp를 반환해야 한다")
    void healthCheck_returnsStatusAndTimestamp() throws Exception {
        mockMvc.perform(get("/health"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is("UP")))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.timestamp").isString());
    }
}