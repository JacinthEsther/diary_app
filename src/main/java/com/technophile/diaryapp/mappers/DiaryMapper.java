package com.technophile.diaryapp.mappers;

import com.technophile.diaryapp.controllers.DiaryDTO;
import com.technophile.diaryapp.models.Diary;
import org.mapstruct.Mapper;

@Mapper
public interface DiaryMapper {
    DiaryDTO diaryToDiaryDTO(Diary diary);
}

