package com.example.TallerSpringApi.controller;

import com.example.TallerSpringApi.dto.*;
import com.example.TallerSpringApi.service.ApiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {
    private final ApiService apiService;

    @Autowired
    public MainController(ApiService apiService){
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String showLoginForm(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
                        BindingResult result, Model model){
        if(result.hasErrors()){
            return "login";
        }

        try{
            LoginResponse response = apiService.authenticate(loginRequest);

            User user = apiService.getUserByUsername(loginRequest.getUsername());
            if (user == null) {
                model.addAttribute("error", "No se encontró el usuario");
                return "login";
            }

            model.addAttribute("token", response.getToken());
            model.addAttribute("userId", user.getId());
            model.addAttribute("products", apiService.getProducts());
            return "products";
        }catch (Exception e){
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }

    @GetMapping("/carts/{userId}")
    public String getUserCarts(@PathVariable Long userId, Model model) {
        List<Cart> carts = apiService.getUserCarts(userId);
        model.addAttribute("carts", carts);
        return "carts";
    }
}