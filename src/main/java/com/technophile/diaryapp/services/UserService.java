package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;
import com.technophile.diaryapp.controllers.responses.UserDTO;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.User;

public interface UserService {
    UserDTO createAccount(CreateAccountRequest accountRequestDTO);
    String updateAccount(String id, UpdateAccountRequest updateAccountRequestDTO);

    String deleteAccount(String email );

    UserDTO getUserBy(String id);

    Diary addNewDiary(String userId, Diary diary);

    User findUserByIdInternal(String userId);

    void deleteByEmail(String email);
}
