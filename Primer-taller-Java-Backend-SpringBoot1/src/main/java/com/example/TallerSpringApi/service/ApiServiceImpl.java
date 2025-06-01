package com.example.TallerSpringApi.service;

import com.example.TallerSpringApi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService {
    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://fakestoreapi.com";

    @Autowired
    public ApiServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        return restTemplate.postForObject(BASE_URL + "/auth/login", loginRequest, LoginResponse.class);
    }

    @Override
    public List<Product> getProducts() {
        Product[] products = restTemplate.getForObject(BASE_URL + "/products", Product[].class);
        return Arrays.asList(products);
    }

    @Override
    public List<Cart> getUserCarts(Long userId) {
        Cart[] carts = restTemplate.getForObject(BASE_URL + "/carts/user/" + userId, Cart[].class);
        return Arrays.asList(carts);
    }

    @Override
    public User getUserByUsername(String username) {
        User[] users = restTemplate.getForObject(BASE_URL + "/users", User[].class);
        if (users != null) {
            for (User user : users) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
        }
        return null;
    }
}
