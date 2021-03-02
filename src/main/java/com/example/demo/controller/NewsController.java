package com.example.demo.controller;

import com.example.demo.dto.news.DtoFromViewToDTB;
import com.example.demo.model.New;
import com.example.demo.model.RegistrationForm;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repo.NewsRepo;
import com.example.demo.service.NewsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class NewsController {
    private NewsService newsService;
    private NewsRepo newsRepo;

    public NewsController(NewsService newsService, NewsRepo newsRepo) {
        this.newsService = newsService;
        this.newsRepo = newsRepo;
    }

    @GetMapping("/getNews")
    public String getNews(Model model) {
        model.addAttribute("allNews", newsService.getAllNews());

        return "news";
    }

    @GetMapping("/addNews")
    public String getNews(@ModelAttribute("newsForm") DtoFromViewToDTB dto) {
        return "addNews";
    }

    @PostMapping("/addNews")
    public String createNew(@ModelAttribute("newsForm") DtoFromViewToDTB dto) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("error");
//            return "redirect:/";
//        }
        newsService.saveNew(dto.createNew());
        return "redirect:/getNews";
    }

    @GetMapping("/{id}/show")
    public String showNew(@PathVariable("id") Long id, @AuthenticationPrincipal User user, Model model) {
        System.out.println(newsService.findById(id));
            if (user.getAuthorities().contains(Role.ADMIN)) {
                model.addAttribute("flag", true);
                System.out.println(user.getAuthorities().toString());
            }
            model.addAttribute("n", newsService.findById(id));

            return "view";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id, Model model) {
        newsService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("editNew", newsService.findById(id));
        return "editNews";
    }

    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") Long id,@ModelAttribute("editNew") New newd){
        newsService.save(newd);
        return "redirect:/";
    }
}
