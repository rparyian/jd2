package com.example.demo.controller;

import com.example.demo.model.RegistrationForm;
import com.example.demo.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private PasswordEncoder passwordEncoder;
    private UserRepo userRepo;

    public RegistrationController(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String getRegistrationForm(){
        return "/registration";
    }
    @PostMapping
    public String saveNewUser(RegistrationForm form){
        System.out.println(form.getPassword());
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
