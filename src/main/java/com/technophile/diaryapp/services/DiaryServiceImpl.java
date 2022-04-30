package com.technophile.diaryapp.services;

import com.technophile.diaryapp.exceptions.DiaryApplicationException;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.User;
import com.technophile.diaryapp.repositories.DiaryRepository;
import com.technophile.diaryapp.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@NoArgsConstructor
@Service
public class DiaryServiceImpl implements DiaryService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiaryRepository diaryRepository;
    @Override
    public Diary createDiary(String title, String id) {
        User user = userRepository.findById(id).
                orElseThrow(()-> new DiaryApplicationException("user not found"));

        Diary diary = new Diary(title, user);
        //save method returns the object
      return  diaryRepository.save(diary);
    }
}
