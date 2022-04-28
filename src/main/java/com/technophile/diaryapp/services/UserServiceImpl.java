package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.exceptions.DiaryApplicationException;
import com.technophile.diaryapp.models.User;
import com.technophile.diaryapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
//    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public String createAccount(CreateAccountRequest accountRequestDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmail(accountRequestDTO.getEmail());
        if(optionalUser.isPresent()){
            throw new DiaryApplicationException("User already exist");
        }

        User user = new User();
        user.setEmail(accountRequestDTO.getEmail());
        user.setPassword(accountRequestDTO.getPassword());
//        userRepository.save(user);
      User  savedUser = userRepository.save(user);
        return savedUser.getId();
    }
}
