package com.practicePoc.service.impl;

import com.practicePoc.entity.User;
import com.practicePoc.exception.UserException;
import com.practicePoc.payload.UserDto;
import com.practicePoc.repository.UserRepository;
import com.practicePoc.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private String string;

    @Override
    public UserDto createUser(UserDto userDto) {

        return entityToDto(userRepository.save(dtoToEntity(userDto)));
    }

    @Override
    public UserDto userUpdate(UserDto userDto, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("user", "id"));
        User save = userRepository.save(user);
        UserDto userDto1 = entityToDto(save);
        return userDto1;
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("User Not found this "+id ," id"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> allUser = userRepository.findAll();
        List<UserDto> userDtos = allUser.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteById(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(()->new UserException("User Not found this "+id ,"id"));
        this.userRepository.delete(user);
    }

    private User dtoToEntity(UserDto userDto) {
        User user = new User();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        BeanUtils.copyProperties(userDto, user);
        return user;
    }

    private UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return userDto;
    }
}
