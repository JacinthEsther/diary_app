package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;
import com.technophile.diaryapp.controllers.responses.UserDTO;
import com.technophile.diaryapp.exceptions.DiaryApplicationException;
import com.technophile.diaryapp.mappers.UserMapper;
import com.technophile.diaryapp.mappers.UserMapperImpl;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class UserServiceImplTest {
@Autowired
    private UserService userService;

private UserMapper userMapper= new UserMapperImpl();

   private CreateAccountRequest createAccount;
    @BeforeEach
    void setUp() {
    createAccount = CreateAccountRequest.builder().email("test@gmail.com")
                .password("password").build();
    }

    @Test
    void testCreateAccount(){


        UserDTO userDTO= userService.createAccount(createAccount);
        assertThat(userDTO.getId()).isNotNull();
        assertThat(userDTO.getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    @DisplayName("when you create a user account with an email that already exist in the db, create account service" +
            "throws diaryexception with the message, user already exist")
    void testThatThrowsExceptionWhenAUserAlreadyExists(){
        userService.createAccount(createAccount);
        CreateAccountRequest createAccount = CreateAccountRequest.builder().email("test@gmail.com")
                .password("password").build();

        assertThatThrownBy(()->userService.createAccount(createAccount)).isInstanceOf
                (DiaryApplicationException.class).hasMessage("User already exist");
    }



    @Test

    void testThatThrowsExceptionWhenUserIdIsNotFound(){
        userService.createAccount(createAccount);
      String id = "null id";
        UpdateAccountRequest updateDTO = UpdateAccountRequest.builder()
                .password("new password")
                .email("newtest@gmail.com").build();

        assertThatThrownBy(()->userService.updateAccount(id, updateDTO)).isInstanceOf
                (DiaryApplicationException.class).hasMessage("user does not exist");
    }

    @Test
    void testThatCanUpdateUserInformation(){
       UserDTO userDTO= userService.createAccount(createAccount);
       UpdateAccountRequest updateRequest= new UpdateAccountRequest("", "newest password");

       String result = userService.updateAccount(userDTO.getId(), updateRequest);
       assertThat(result).isEqualTo("user details updated successfully");
       UserDTO userFromDatabase = userService.getUserBy(userDTO.getId());
       assertThat(userFromDatabase.getEmail()).isEqualTo("test@gmail.com");

    }
//
//    @Disabled
//    void testDeleteAccount(){
//
//
////        String id= userService.createAccount(createAccount);
////        assertThat(id).isNotNull();
//
//
////       userService.deleteAccount("test@gmail.com");
//
////        assertThat();
//
//    }

    @Test
    void testThatCanGetUserInformation(){
        UserDTO userDTOFromDataBase= userService.createAccount(createAccount);

   UserDTO userDTO = userService.getUserBy(userDTOFromDataBase.getId());
   assertThat(userDTO.getId()).isEqualTo(userDTOFromDataBase.getId());
    }



    @Test
    void testThatCanAddDiaryToUser(){


        UserDTO userDTO=  userService.createAccount(createAccount);
//        UserDTO userDTO=userService.getUserBy(userId);
//        User user = userMapper.userDTOToUser(userDTO);
        User user = userService.findUserByIdInternal(userDTO.getId());
        String diaryTitle = "diary title";

        Diary diary = new Diary(diaryTitle, user);
        Diary savedDiary = userService.addNewDiary(user.getId(), diary);

        assertThat(savedDiary.getId()).isNotNull();
        assertThat(savedDiary.getTitle()).isEqualTo("diary title");
    }

    @AfterEach
    void tearDown() {
        userService.deleteByEmail("test@gmail.com");
    }

    //    @Disabled
//    @Test
//    void testGetUserByEmail(){
//        CreateAccountRequest createAccount = CreateAccountRequest.builder().email("test@gmail.com")
//                .password("password").build();
//
//        String id= userService.createAccount(createAccount);
//        assertThat(id).isNotNull();
//
//        userService.getUserBy("test@gmail.com");
//    }
}