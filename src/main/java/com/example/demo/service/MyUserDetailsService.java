package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;
    public MyUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }



    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return userRepo.findByUsername(name);
    }
    public void changeStatusToUnblocked(User user){
        user.setStatus("unblocked");
        userRepo.saveAndFlush(user);
    }
    public void changeStatusToBlocked(User user){
        user.setStatus("blocked");
        userRepo.saveAndFlush(user);

    }

    public void changeLastInTime(User user){
        user.setLastInDate(Timestamp.valueOf(LocalDateTime.now()));
        userRepo.saveAndFlush(user);
        System.out.println(user.getLastInDate());
    }
}
