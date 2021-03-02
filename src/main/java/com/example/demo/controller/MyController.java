package com.example.demo.controller;

import com.example.demo.config.SecurityConfig;
import com.example.demo.model.User;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.service.NewsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class MyController {

    private UserRepo userRepo;
    private SecurityConfig securityConfig;
    private MyUserDetailsService myUserDetailsService;
    private NewsService newsService;

    public MyController(SecurityConfig securityConfig,UserRepo userRepo,
                        MyUserDetailsService myUserDetailsService,NewsService newsService) {
        this.userRepo = userRepo;
        this.myUserDetailsService=myUserDetailsService;
        this.securityConfig=securityConfig;
        this.newsService=newsService;
    }


    @RequestMapping("/")
    public String getIndex(@AuthenticationPrincipal User user, Model model,HttpServletRequest request){
        if (user!=null){
            model.addAttribute("allNews",newsService.getAllNews());
            return "news";
        }
        model.addAttribute("user","anonymous");
        model.addAttribute("allNews",newsService.getAllNews());
        return "login";
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
    public String block(HttpServletRequest httpServletRequest, Authentication authentication) throws ServletException {
        boolean flag=false;
        User user = (User)authentication.getPrincipal();
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
        System.out.println(authentication.getPrincipal());
        System.out.println(httpServletRequest.getSession());
        if (httpServletRequest.getParameterValues("userName") != null) {
            for (String userName : httpServletRequest.getParameterValues("userName")) {
                myUserDetailsService.changeStatusToUnblocked(userRepo.findByUsername(userName));
            }
        }
        return "redirect:forUser";

    }
    public void expireSession(User user){
        List<SessionInformation> sessions = securityConfig.sessionRegistry().getAllSessions(user, false);
        System.out.println(sessions.size());
    }
}
