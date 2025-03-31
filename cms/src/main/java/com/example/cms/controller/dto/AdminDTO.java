package com.example.cms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private long adminID;
    private long userID;
    private String firstName;
    private String lastName;
    private String email;
}

