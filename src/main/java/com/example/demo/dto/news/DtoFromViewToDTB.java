package com.example.demo.dto.news;

import com.example.demo.model.New;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class DtoFromViewToDTB {
    private String title;
    private String brief;
    private String content;
    public New createNew(){
        New newNew = new New();
        newNew.setTitle(title);
        newNew.setBrief(brief);
        newNew.setContent(content);
        newNew.setDateOfCreation(Timestamp.valueOf(LocalDateTime.now()));
        return newNew;
    }
}
