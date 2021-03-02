package com.example.demo.model;

import lombok.Data;
import lombok.var;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;

@Data
public class RegistrationForm {
    private Long id;
    @NotBlank(message = "should not be empty")
    @Size(min=2, message = "min 2")
    private String username;
    @NotBlank(message = "should not be empty")
    @Email(message = "shoud be email")
    private String email;
    @Size(min=1, message = "min 1")
    private String password;
   // private String passwordConfirm;

    public User toUser(PasswordEncoder passwordEncoder){
        User user=new User();
        user.setEmail(email);
        user.setId(id);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));
        user.setRegistrationDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setLastInDate(Timestamp.valueOf(LocalDateTime.now()));
        user.setStatus("unblocked");
        return user;
    }
}
