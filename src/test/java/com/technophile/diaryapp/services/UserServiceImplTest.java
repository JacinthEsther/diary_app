package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UserServiceImplTest {
@Autowired
    private UserService userService;


    @Test
    void testCreateAccount(){
        CreateAccountRequest createAccount = CreateAccountRequest.builder().email("test@gmail.com")
                .password("password").build();

        String id= userService.createAccount(createAccount);
        assertThat(id).isNotNull();
    }

    @Test
    void testUpdateAccount(){
        CreateAccountRequest createAccount = CreateAccountRequest.builder().email("test@gmail.com")
                .password("password").build();

        String id= userService.createAccount(createAccount);
        assertThat(id).isNotNull();

        UpdateAccountRequest updateAccount = UpdateAccountRequest.builder().email("test@gmail.com")
                .oldPassword("password").newPassword("technophiles").build();
        userService.updateAccount(updateAccount);
        assertThat("technophiles").isEqualTo(updateAccount.getNewPassword());
    }

    @Test
    void testDeleteAccount(){
        CreateAccountRequest createAccount = CreateAccountRequest.builder().email("test@gmail.com")
                .password("password").build();

        String id= userService.createAccount(createAccount);
        assertThat(id).isNotNull();


       userService.deleteAccount("test@gmail.com");

//        assertThat();

    }

    @Test
    void testGetUserByEmail(){
        CreateAccountRequest createAccount = CreateAccountRequest.builder().email("test@gmail.com")
                .password("password").build();

        String id= userService.createAccount(createAccount);
        assertThat(id).isNotNull();

        userService.getUserBy("test@gmail.com");
    }
}