package com.technophile.diaryapp.controllers;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;
import com.technophile.diaryapp.controllers.responses.APIResponse;
import com.technophile.diaryapp.exceptions.DiaryApplicationException;
import com.technophile.diaryapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createNewUserAccount(@RequestBody CreateAccountRequest accountRequest){
try {
    APIResponse response = APIResponse.builder()
            .message("id: " + userService
                    .createAccount(accountRequest))
            .isSuccessful(true)
            .build();
    return new ResponseEntity<>(response, HttpStatus.CREATED);
}
catch(DiaryApplicationException ex){
    APIResponse response = APIResponse.builder()
            .message(ex.getMessage())
            .isSuccessful(false)
            .build();
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
}
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateAccount(@RequestBody UpdateAccountRequest updateRequest){
try {
    APIResponse response = APIResponse.builder()
            .message("account " + userService
                    .updateAccount(updateRequest))
            .isSuccessful(true)
            .build();
    return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
}
catch(DiaryApplicationException ex){
    APIResponse response = APIResponse.builder()
            .message(ex.getMessage())
            .isSuccessful(false)
            .build();
    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
}
    }


    @DeleteMapping("/delete/{email}")
    public ResponseEntity<?> updateAccount(@PathVariable String email){
try {
    APIResponse response = APIResponse.builder()
            .message("user " + userService
                    .deleteAccount(email))
            .isSuccessful(true)
            .build();
    return new ResponseEntity<>(response, HttpStatus.OK);
}
catch(DiaryApplicationException ex){
    APIResponse response = APIResponse.builder()
            .message(ex.getMessage())
            .isSuccessful(false)
            .build();
    return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
}
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<?> getAccount(@PathVariable String email){
try {
    APIResponse response = APIResponse.builder()
            .message(userService
                    .getUserBy(email))
            .isSuccessful(true)
            .build();
    return new ResponseEntity<>(response, HttpStatus.FOUND);
}
catch(DiaryApplicationException ex){
    APIResponse response = APIResponse.builder()
            .message(ex.getMessage())
            .isSuccessful(false)
            .build();
    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
}
    }
}
