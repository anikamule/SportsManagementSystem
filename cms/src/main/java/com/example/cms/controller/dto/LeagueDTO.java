package com.example.cms.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeagueDTO {


    @NotEmpty(message = "League name is required.")
    private String leagueName;


    @NotEmpty(message = "League sport is required.")
    private String leagueSport;


    @NotEmpty(message = "League gender is required.")
    private String leagueGender;
}

