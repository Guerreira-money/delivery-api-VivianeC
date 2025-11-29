package com.deliverytech.delivery_api.integration.controllers;

import com.deliverytech.delivery_api.dto.auth.LoginRequest;
import com.deliverytech.delivery_api.dto.auth.LoginResponse;
import com.deliverytech.delivery_api.dto.auth.RegisterRequest;
import com.deliverytech.delivery_api.dto.auth.UserResponse;
import com.deliverytech.delivery_api.entity.Usuario;
import com.deliverytech.delivery_api.enums.Role;
import com.deliverytech.delivery_api.repository.UsuarioRepository;
import com.deliverytech.delivery_api.security.SecurityUtils;
import com.deliverytech.delivery_api.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
public class AuthControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void deveFazerLoginComSucesso() throws Exception {

        LoginRequest loginRequest = LoginRequest.builder()
                .email("email@email.com")
                .senha("senha123")
                .build();

        LoginResponse response = LoginResponse.builder()
                .tipoToken("Bearer")
                .token("jwt-token")
                .build();

        when(authService.login(Mockito.any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.tipoToken").value("Bearer"));
    }

    @Test
    void deveRegistrarUsuarioComSucesso() throws Exception {

        RegisterRequest request = RegisterRequest.builder()
                .nome("João")
                .email("joao@email.com")
                .senha("senha123")
                .role(Role.CLIENTE)
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .nome("João")
                .email("joao@email.com")
                .role(Role.CLIENTE)
                .build();

        when(authService.register(Mockito.any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.email").value("joao@email.com"))
                .andExpect(jsonPath("$.role").value("CLIENTE"));
    }

    @Test
    void deveRetornarUsuarioLogadoComSucesso() throws Exception {

        String email = "admin@delivery.com";

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail(email);
        usuario.setNome("Admin");
        usuario.setRole(Role.ADMIN);

        try (var mocked = Mockito.mockStatic(SecurityUtils.class)) {

            mocked.when(SecurityUtils::getCurrentUsername)
                    .thenReturn(email);

            when(usuarioRepository.findByEmail(email))
                    .thenReturn(Optional.of(usuario));

            mockMvc.perform(get("/api/auth/me"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value(email))
                    .andExpect(jsonPath("$.nome").value("Admin"))
                    .andExpect(jsonPath("$.role").value("ADMIN"));
        }
    }

    @Test
    void deveRetornar401SeNaoEstiverLogado() throws Exception {

        try (var mocked = Mockito.mockStatic(SecurityUtils.class)) {

            mocked.when(SecurityUtils::getCurrentUsername)
                    .thenReturn(null);

            mockMvc.perform(get("/api/auth/me"))
                    .andExpect(status().isUnauthorized());
        }
    }
}
