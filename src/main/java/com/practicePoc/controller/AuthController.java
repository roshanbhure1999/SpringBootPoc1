package com.practicePoc.controller;

import com.practicePoc.exception.UserException;
import com.practicePoc.payload.JwtAuthRequest;
import com.practicePoc.payload.UserDto;
import com.practicePoc.security.JwtAuthResponse;
import com.practicePoc.security.JwtTokenHelper;
import com.practicePoc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {

        this.authenticate(request.getUserName(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
    }

    private void authenticate(String userName, String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);

       try {
           authenticationManager.authenticate(authenticationToken);
       }catch (BadCredentialsException e){
           System.out.println("Invalid Details !!");
         throw   new UserException("Invalid user name or password",HttpStatus.BAD_REQUEST);

       }
    }
    @PostMapping("/register")
    public  ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto userDto1 = this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(userDto1,HttpStatus.CREATED);
    }

}
