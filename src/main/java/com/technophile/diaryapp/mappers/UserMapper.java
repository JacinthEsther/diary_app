package com.technophile.diaryapp.mappers;

import com.technophile.diaryapp.controllers.responses.UserDTO;
import com.technophile.diaryapp.models.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
}
