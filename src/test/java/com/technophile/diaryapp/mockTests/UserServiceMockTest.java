package com.technophile.diaryapp.mockTests;

import com.technophile.diaryapp.DataConfig;
import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.responses.UserDTO;
import com.technophile.diaryapp.models.User;
import com.technophile.diaryapp.repositories.UserRepository;
import com.technophile.diaryapp.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@ComponentScan(basePackages = "com.technophiles.diaryapp.**")
@ContextConfiguration(classes = {DataConfig.class})
public class UserServiceMockTest {
    @Mock
    private UserRepository userRepository;
    private UserServiceImpl userService;
//    @Captor
//    ArgumentCaptor<User> userArgumentCaptor;


    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository);
    }
    @Test
    void testThatCanCreateAccount(){
        //given
        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .email("testemail@gmail.com")
                .password("password")
                .build();
        //when
        User user = new User("dummy id", "testemail@gmail.com", "password" );
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);
        UserDTO userDTO = userService.createAccount(accountRequest);
//        verify(userRepository,times(1)).save(userArgumentCaptor.capture());
//        User capturedUser = userArgumentCaptor.getValue();
//        assertThat(capturedUser.getPassword()).isEqualTo("password");
//        assertThat(capturedUser.getEmail()).isEqualTo("testemail@gmail.com");
        assertThat(userDTO.getId()).isEqualTo("dummy id");
        assertThat(userDTO.getEmail()).isEqualTo("testemail@gmail.com");
    }
}