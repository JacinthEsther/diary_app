package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.DiaryDTO;
import com.technophile.diaryapp.controllers.requests.UpdateDiaryForm;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.Entry;

import java.util.List;

public interface DiaryService {

    Diary createDiary(String title, String id);

    String updateDiary(String diaryId, UpdateDiaryForm updateDiaryForm);

    Diary addEntries(List<Entry> entries, String diaryId);

    DiaryDTO getDiaryBy(String id);

    void deleteDiary(String id);

    Diary findDiary(String id);
}
