package com.technophile.diaryapp.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter

@Document
public class Diary {

    @Id
    private String id;
    private String title;
    private LocalDateTime creationTime;
    @DBRef
    //database reference, diary is related to user
    private User owner;

    public Diary(String title, User owner) {
        this.title = title;
        this.creationTime = LocalDateTime.now();
        this.owner = owner;
    }
}
