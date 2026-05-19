package com.example.frankenstein.controller;

import com.example.frankenstein.model.UserAccount;
import com.example.frankenstein.repository.UserAccountRepository;
import com.example.frankenstein.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "app.security.seed.admin-username=testadmin",
        "app.security.seed.admin-password=testpass123"
})
class AuthorSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldBlockDeleteWithoutJwt() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRejectInvalidJwt() throws Exception {
        mockMvc.perform(delete("/api/v1/authors/1")
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }

    @Test
    void shouldAllowDeleteForAdminWithJwt() throws Exception {
        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Map.of(
                                "username", "testadmin",
                                "password", "testpass123"
                        ))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = objectMapper.readTree(response).get("token").asText();

        mockMvc.perform(delete("/api/v1/authors/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldBlockDeleteForNonAdminUser() throws Exception {
        UserAccount user = new UserAccount();
        user.setUsername("reader");
        user.setPassword(passwordEncoder.encode("reader123"));
        user.setRole("USER");
        userAccountRepository.save(user);

        String token = jwtService.generateToken("reader");

        mockMvc.perform(delete("/api/v1/authors/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}
