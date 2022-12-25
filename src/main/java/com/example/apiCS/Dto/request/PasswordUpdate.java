package com.example.apiCS.Dto.request;

import lombok.Data;

@Data
public class PasswordUpdate {
    private String oldPassword;
    private String newPassword;
}
