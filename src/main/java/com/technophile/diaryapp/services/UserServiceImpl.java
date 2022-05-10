package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;
import com.technophile.diaryapp.controllers.responses.UserDTO;
import com.technophile.diaryapp.exceptions.DiaryApplicationException;
import com.technophile.diaryapp.mappers.UserMapper;
import com.technophile.diaryapp.mappers.UserMapperImpl;
import com.technophile.diaryapp.models.Diary;
import com.technophile.diaryapp.models.User;
import com.technophile.diaryapp.repositories.DiaryRepository;
import com.technophile.diaryapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

   private  DiaryRepository diaryRepository;
    private UserMapper userMapper = new UserMapperImpl();


    public UserServiceImpl(UserRepository userRepository,DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.diaryRepository= diaryRepository;
    }

    public UserServiceImpl() {

    }


    @Override
    public UserDTO createAccount(CreateAccountRequest accountRequestDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmail(accountRequestDTO.getEmail());
        if(optionalUser.isPresent()){
            throw new DiaryApplicationException("User already exist");
        }

        User user = new User();
        user.setEmail(accountRequestDTO.getEmail().toLowerCase());
        user.setPassword(accountRequestDTO.getPassword());
        user.setDiaries(new HashSet<>());
//        userRepository.save(user);
      User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public String updateAccount(String id, UpdateAccountRequest updateAccountRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(()->
                new DiaryApplicationException("user does not exist"));

        boolean isUpdated= false;

        if(!(updateAccountRequestDTO.getEmail()==null || updateAccountRequestDTO.getEmail().trim().equals(""))) {
            user.setEmail(updateAccountRequestDTO.getEmail());
            isUpdated= true;
        }

        if(!(updateAccountRequestDTO.getPassword()==null || updateAccountRequestDTO.getPassword().trim().equals(""))) {
            user.setPassword(updateAccountRequestDTO.getPassword());
            isUpdated=true;
        }

        if (isUpdated) {
            userRepository.save(user);
        }
        return "user details updated successfully";
    }

    @Override
    public String deleteAccount(String email) {
        Optional <User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return "deleted successfully";
        }
        throw new DiaryApplicationException("user does not exist");


    }

    @Override
    public UserDTO getUserBy(String id) {
   User user = userRepository.findById(id).orElseThrow(()->
           new DiaryApplicationException("user does not exist"));
   return userMapper.userToUserDTO(user);
    }

    @Override
    public Diary addNewDiary(String userId, Diary diary) {
     User user= userRepository.findById(userId).orElseThrow(()->
             new DiaryApplicationException("user does not exist"));
     Diary savedDiary = diaryRepository.save(diary);
     user.getDiaries().add(savedDiary);
     userRepository.save(user);
        return savedDiary;
    }

    @Override
    public User findUserByIdInternal(String userId) {
        return userRepository.findById(userId).orElseThrow(()->
                new DiaryApplicationException("user does not exist"));
    }

    @Override
    public void deleteByEmail(String email) {
       User user= userRepository.findUserByEmail(email).orElseThrow(()->
               new DiaryApplicationException("user does not exist"));

       userRepository.delete(user);

    }
}
