package com.technophile.diaryapp.services;


import com.technophile.diaryapp.models.Diary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
public class DiaryServiceTest {
    @Autowired
    private DiaryService diaryService;


    @Test
    void testThatCanCreateANewDiary(){
        String title= new String ("new diary title");
        String id = new String("626bc3d81f23d64f8127d3cd");
        Diary diary = diaryService.createDiary(title, id);

        assertThat(diary.getTitle()).isEqualTo("new diary title");
    }
}
