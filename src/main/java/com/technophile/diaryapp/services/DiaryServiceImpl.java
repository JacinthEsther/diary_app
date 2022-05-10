package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.UpdateDiaryForm;
import com.technophile.diaryapp.exceptions.DiaryApplicationException;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.Entry;
import com.technophile.diaryapp.models.User;
import com.technophile.diaryapp.repositories.DiaryRepository;
import com.technophile.diaryapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;
    @Override
    public Diary createDiary(String diaryTitle, String userId) {
        User user = userRepository.findById(userId).
                orElseThrow(()-> new DiaryApplicationException("user not found"));

        Diary diary = new Diary(diaryTitle);
        Diary savedDiary = diaryRepository.save(diary);
        user.getDiaries().add(savedDiary);
        userRepository.save(user);
        //save method returns the object
      return  savedDiary;
    }

    @Override
    public String updateDiary(String diaryId, UpdateDiaryForm updateDiaryForm){
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(()->
                new DiaryApplicationException("diary does not exist"));

        if(!(updateDiaryForm.getTitle().trim().equals(""))||updateDiaryForm.getTitle()==null){
            diary.setTitle(updateDiaryForm.getTitle());
            diaryRepository.save(diary);
        }
        return "Diary has been updated";
    }


    @Override
    public Diary addEntries(List<Entry> entries, String diaryId){
        Diary diary = diaryRepository.findById(diaryId).orElseThrow(()->
                new DiaryApplicationException("Diary does not exist"));

        diary.getEntries().addAll(entries);
        return diaryRepository.save(diary);
    }
}
