package com.deliverytech.delivery_api.dto.auth;

import com.deliverytech.delivery_api.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String nome;
    private String email;
    private Role role;
}
