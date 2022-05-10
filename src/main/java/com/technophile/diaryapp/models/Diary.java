package com.technophile.diaryapp.models;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@Document
public class Diary {

    @Id
    private String id;
    private String title;
    private LocalDateTime creationTime;
   private Set<Entry> entries;

    public Diary(String title) {
        this.title = title;
        this.creationTime = LocalDateTime.now();
        this.entries= new HashSet<Entry>();

    }

    public Diary(String id, String title){
        this.id = id;
        this.title = title;
        this.creationTime=LocalDateTime.now();
        this.entries= new HashSet<Entry>();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Diary{");
        sb.append("id='").append(id).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", creationTime=").append(creationTime);
        sb.append('}');
        return sb.toString();
    }
}
