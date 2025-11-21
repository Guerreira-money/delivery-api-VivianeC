package com.deliverytech.delivery_api.service;

import com.deliverytech.delivery_api.dto.auth.LoginRequest;
import com.deliverytech.delivery_api.dto.auth.LoginResponse;
import com.deliverytech.delivery_api.dto.auth.RegisterRequest;
import com.deliverytech.delivery_api.dto.auth.UserResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);

    UserResponse register(RegisterRequest request);
}
