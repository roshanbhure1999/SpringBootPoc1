package com.practicePoc.controller;

import com.practicePoc.exception.UserException;
import com.practicePoc.payload.UserDto;
import com.practicePoc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto user = userService.createUser(userDto);
        return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<UserDto> updateUser( @Valid @RequestBody UserDto userDto, @PathVariable Long id) {
        UserDto userDto1 = userService.userUpdate(userDto, id);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.OK);
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        UserDto userDto1 = userService.getById(id);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAllUSer() {
        List<UserDto> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<UserException> deleteUser(@PathVariable Long id) {
        this.userService.deleteById(id);
        return new ResponseEntity<UserException>(new UserException("User is delete successfully",HttpStatus.OK), HttpStatus.OK);
    }
}
