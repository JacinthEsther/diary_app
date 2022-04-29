package com.technophile.diaryapp.services;

import com.technophile.diaryapp.controllers.requests.CreateAccountRequest;
import com.technophile.diaryapp.controllers.requests.UpdateAccountRequest;
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
        user.setEmail(accountRequestDTO.getEmail().toLowerCase());
        user.setPassword(accountRequestDTO.getPassword());
//        userRepository.save(user);
      User  savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Override
    public String updateAccount(UpdateAccountRequest updateAccountRequestDTO) {
        Optional<User> optionalUser = userRepository.findUserByEmail(updateAccountRequestDTO.getEmail()
                .toLowerCase());
        boolean userIsValid = optionalUser.isPresent()&& updateAccountRequestDTO.getOldPassword()
                .equals(optionalUser.get().getPassword());
        if(userIsValid){
            optionalUser.get().setEmail(updateAccountRequestDTO.getEmail());
            optionalUser.get().setPassword(updateAccountRequestDTO.getNewPassword());

            User savedUser = userRepository.save(optionalUser.get());
            return savedUser.getId();
        }
        throw new DiaryApplicationException("user does not exist");
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
    public String getUserBy(String email) {
        Optional <User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getPassword()+" " + " "+ optionalUser.get().getEmail();
        }
        throw new DiaryApplicationException("user does not exist");
    }
}
