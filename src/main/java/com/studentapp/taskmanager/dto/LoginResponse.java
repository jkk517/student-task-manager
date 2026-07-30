package com.studentapp.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private Long userId;
    private String username;
}
