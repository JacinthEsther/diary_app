package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;

public interface UserService {
    String createAccount(CreateAccountRequest accountRequestDTO);
    String updateAccount(UpdateAccountRequest updateAccountRequestDTO);

    String deleteAccount(String email );

    String getUserBy(String email);
}
