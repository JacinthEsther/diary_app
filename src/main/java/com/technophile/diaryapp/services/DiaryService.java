package com.technophile.diaryapp.services;

import com.technophile.diaryapp.models.Diary;

public interface DiaryService {

    Diary createDiary(String title, String id);
}
