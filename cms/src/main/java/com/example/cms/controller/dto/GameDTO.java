package com.example.cms.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private long gameID;
    private LocalDateTime datetime;
    private String location;
    private String teamID1;
    private String teamID2;
    private int team1score;
    private int team2score;
    private String gameStatus;
    private long sportID;
    private long divisionID;
}

