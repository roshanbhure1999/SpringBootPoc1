package com.practicePoc.service;

import com.practicePoc.payload.UserDto;
import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto userUpdate(UserDto userDto, Long id) ;

    UserDto getById(Long id);

    List<UserDto> getAllUser();

    void deleteById(Long id);
}
