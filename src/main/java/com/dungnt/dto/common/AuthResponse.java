package com.dungnt.dto.common;

import lombok.Getter;

@Getter
public class AuthResponse {
    public String token;

    public AuthResponse() {}
    public AuthResponse(String token) {
        this.token = token;
    }
}