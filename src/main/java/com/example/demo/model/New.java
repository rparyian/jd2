package com.example.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String brief;
    private String content;
    private Timestamp dateOfCreation;
//    @ManyToOne (optional=false, cascade=CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User creator;



}
