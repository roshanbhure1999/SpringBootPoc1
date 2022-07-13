package com.practicePoc.payload;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class UserDto {

    private Long id;

    @NotEmpty(message = "Name.required ")
    @Size(min = 4,message = "user name must be min 4 character !")
    private String name;

    @Email(message = "email.required ")
    private String email;

    @NotEmpty(message = "password.required ")
    @Size(min = 3,max = 10, message = "password must be min of 3 character and max of 10 character ")

    private String password;

    @NotEmpty(message = "about.required ")
    private String about;

}
