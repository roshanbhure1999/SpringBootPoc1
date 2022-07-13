package com.practicePoc.exception;

import lombok.Data;

@Data
public class ApiResponse {
    private String message;
    private boolean b;

    public ApiResponse(String message, boolean b) {
        this.message = message;
        this.b = b;
    }
}
