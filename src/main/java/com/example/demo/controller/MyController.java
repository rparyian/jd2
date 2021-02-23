package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.MyUserDetailsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


@Controller
public class MyController {
    private UserRepo userRepo;
    private MyUserDetailsService myUserDetailsService;
    public MyController(UserRepo userRepo, MyUserDetailsService myUserDetailsService) {
        this.userRepo = userRepo;
        this.myUserDetailsService=myUserDetailsService;
    }

    @RequestMapping("/")
    public String getIndex(@AuthenticationPrincipal User user, Model model){
        if (user!=null){
            model.addAttribute("user", user.getUsername());
            return "/index";
        }
        model.addAttribute("user","anonymous");
        return "/index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }
    @PreAuthorize(value = "hasAuthority('USER')")
    @GetMapping("/forUser")
    public String getForUser(Model model){
        model.addAttribute("users",userRepo.findAll());
        return "forUser";
    }
    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping("/delete")
    public String delete(HttpServletRequest httpServletRequest, @AuthenticationPrincipal User user) throws ServletException, ServletException {
        boolean flag=false;
        if (httpServletRequest.getParameterValues("userName") != null) {
            for (String userName : httpServletRequest.getParameterValues("userName")) {
                if (userName.equals(user.getUsername()))
                    flag=true;
                userRepo.delete(userRepo.findByUsername(userName));
            }
        }
        if (flag)
            httpServletRequest.logout();
        return "redirect:forUser";
    }
    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping("/block")
    public String block(HttpServletRequest httpServletRequest, @AuthenticationPrincipal User user) throws ServletException {
        boolean flag=false;
        if (httpServletRequest.getParameterValues("userName") != null) {
            for (String userName : httpServletRequest.getParameterValues("userName")) {
                if (userName.equals(user.getUsername()))
                    flag=true;
                myUserDetailsService.changeStatusToBlocked(userRepo.findByUsername(userName));
            }
        }
        if (flag)
            httpServletRequest.logout();
        return "redirect:forUser";
    }
    @PreAuthorize(value = "hasAuthority('USER')")
    @PostMapping("/unblock")
    public String unblock(HttpServletRequest httpServletRequest, Authentication authentication) throws ServletException {
        System.out.println((User)authentication.getPrincipal());
        if (httpServletRequest.getParameterValues("userName") != null) {
            for (String userName : httpServletRequest.getParameterValues("userName")) {
                myUserDetailsService.changeStatusToUnblocked(userRepo.findByUsername(userName));
            }
        }
        return "redirect:forUser";

    }
}
