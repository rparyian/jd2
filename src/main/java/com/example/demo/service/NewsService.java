package com.example.demo.service;

import com.example.demo.model.New;
import com.example.demo.repo.NewsRepo;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class NewsService {
    private NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }
    public Collection<New> getAllNews(){
        return newsRepo.findAll();
    }
    public New saveNew(New newNew){
       return newsRepo.save(newNew);
    }
    public New findById(Long id){
        return newsRepo.findById(id).get();
    }
    public void deleteById(Long id){
        newsRepo.deleteById(id);
    }

    public void save (New newd){
        newsRepo.saveAndFlush(newd);
    }
}
