package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.RegistrationForm;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private SecurityConfig securityConfig;
    private PasswordEncoder passwordEncoder;
    private UserRepo userRepo;

    public RegistrationController( PasswordEncoder passwordEncoder, UserRepo userRepo, SecurityConfig securityConfig) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.securityConfig=securityConfig;
    }

    @GetMapping
    public String getRegistrationForm(){
        return "registration";
    }
    @PostMapping
    public String saveNewUser(RegistrationForm form,@ModelAttribute("user") User user, HttpServletRequest request) throws Exception {
        userRepo.save(form.toUser(passwordEncoder));
        authenticateUserAndSetSession(user,request);
        return "redirect:/";
    }
    private void authenticateUserAndSetSession(User user, HttpServletRequest request) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);// generate session if one doesn't exist
        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = securityConfig.authenticationManagerBean().authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
