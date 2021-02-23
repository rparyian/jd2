package com.example.demo.model;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

@Data
public class RegistrationForm {
    private Long id;
    private String username;
    private String email;
    private String password;
   // private String passwordConfirm;

    public User toUser(PasswordEncoder passwordEncoder){
        User user=new User();
        user.setEmail(email);
        user.setId(id);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        user.setRegistrationDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        user.setLastInDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        user.setStatus("unblocked");
        return user;
    }
}
