package com.example.cms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long userID;
    private String firstName;
    private String lastName;
    private String email;
    //private String role;
}

