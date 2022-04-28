package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;

public interface UserService {
    String createAccount(CreateAccountRequest accountRequestDTO);
}
