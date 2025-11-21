package com.deliverytech.delivery_api.dto.auth;


import com.deliverytech.delivery_api.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String senha;

    @NotNull
    private Role role; // CLIENTE, RESTAURANTE, ADMIN, ENTREGADOR
}
