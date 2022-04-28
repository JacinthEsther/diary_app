package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
}