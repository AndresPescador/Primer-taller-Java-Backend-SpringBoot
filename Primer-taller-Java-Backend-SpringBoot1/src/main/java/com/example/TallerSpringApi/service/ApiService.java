package com.example.TallerSpringApi.service;

import com.example.TallerSpringApi.dto.*;

import java.util.List;

public interface ApiService {
    LoginResponse authenticate(LoginRequest loginRequest);
    List<Product> getProducts();
    List<Cart> getUserCarts(Long userId);
    User getUserByUsername(String username);
}
