package com.practicePoc.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String userName;

    private String password;
}
