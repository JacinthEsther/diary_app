package com.technophile.diaryapp.mockTests;

import com.technophile.diaryapp.DataConfig;
import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;
import com.technophile.diaryapp.controllers.responses.UpdateUserDTO;
import com.technophile.diaryapp.controllers.responses.UserDTO;
import com.technophile.diaryapp.exceptions.DiaryApplicationException;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.User;
import com.technophile.diaryapp.repositories.DiaryRepository;
import com.technophile.diaryapp.repositories.UserRepository;
import com.technophile.diaryapp.services.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DataMongoTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@ComponentScan(basePackages = "com.technophiles.diaryapp.**")
@ContextConfiguration(classes = {DataConfig.class})
public class UserServiceMockTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private DiaryRepository diaryRepository;

    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl();
    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    ArgumentCaptor<Diary> diaryArgumentCaptor;


    //    @BeforeEach
//    void setUp() {
//        userService userRepository);
//    }
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
        verify(userRepository, times(1)).findUserByEmail(accountRequest.getEmail());
        verify(userRepository,times(1)).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser.getPassword()).isEqualTo(accountRequest.getPassword());
        assertThat(capturedUser.getEmail()).isEqualTo(accountRequest.getEmail());
        assertThat(userDTO.getId()).isEqualTo("dummy id");
        assertThat(userDTO.getEmail()).isEqualTo(accountRequest.getEmail());
    }
    @Test
    @DisplayName("When you try to create a user account with an email that already exist in DB," +
            "The create account service should throw a DiaryException with the message: user account already exists")
    void thatTestThrowsDiaryExceptionExceptionWhenEmailAlreadyExists(){
        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .email("testemail@gmail.com")
                .password("password")
                .build();
        when(userRepository.findUserByEmail("testemail@gmail.com")).thenReturn(Optional.of(new User()));
        assertThatThrownBy(()->userService.createAccount(accountRequest)).
                isInstanceOf(DiaryApplicationException.class).hasMessage("User already exist");
    }

    @Test
    void testThatCanGetUserInformation(){
        User user = new User("dummy id", "testemail@gmail.com", "password" );
        when(userRepository.findById("dummy id")).thenReturn(Optional.of(user));
        UserDTO userDTO = userService.getUserBy("dummy id");
        assertThat(userDTO).isNotNull();
    }

    @Test
    void testThatCanUpdateUserInformation(){
        UpdateAccountRequest updateUserDTO = new UpdateAccountRequest("new email", "new password");
        String id = "user id";
        User user = new User("user id", "testemail@gmail.com", "password" );
        User updatedUser = new User("user id", "new email", "new password");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        String expected = userService.updateAccount(id, updateUserDTO);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(user);
        assertThat(expected).isEqualTo("user details updated successfully");
    }

    @Test
    void testThatThrowsExceptionWhenUserIdIsNotFound(){
        UpdateAccountRequest updateUserDTO = new UpdateAccountRequest("new email", "new password");
        String id = "user id";
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->userService.updateAccount(id,updateUserDTO))
                .isInstanceOf(DiaryApplicationException.class).hasMessage("user does not exist");
    }

    @Test
    void testThatCanAddDiaryToUser(){
        String userId = "id";
        Diary diary = new Diary("diary title");
        diary.setId("diary id");
        User user = new User("user id", "new email", "new password");
        user.setDiaries(new HashSet<>());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(diaryRepository.save(any(Diary.class))).thenReturn(diary);
        when(userRepository.save(any(User.class))).thenReturn(user);

        Diary savedDiary = userService.addNewDiary(userId, diary);
        verify(diaryRepository, times(1)).save(diaryArgumentCaptor.capture());
        Diary capturedValue = diaryArgumentCaptor.getValue();
        assertThat(capturedValue.getTitle()).isEqualTo("diary title");
        verify(userRepository, times(1)).save(userArgumentCaptor.capture());

        User savedUser = userArgumentCaptor.getValue();
        assertThat(diary).isIn(savedUser.getDiaries());
    }

}