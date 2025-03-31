package com.example.cms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SportDTO {
    private long sportID;
    private String sportName;
    private int teamSize;
    //private List<DivisionDTO> divisions;
}

