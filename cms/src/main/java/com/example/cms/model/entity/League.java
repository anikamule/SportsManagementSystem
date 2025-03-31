package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "leagues")
public class League {

    @Id
    private String leagueID;

    @NotEmpty
    private String leagueName;

    @NotEmpty
    private String divisionNum;

    @NotEmpty
    private String leagueSport;

    @NotEmpty
    private String leagueGender;

    // CONNECT TEAMS TO LEAGUE
    @OneToMany(mappedBy = "league")
    @Nullable
    @JsonManagedReference("league-teams")
    private List<Team> teams = new ArrayList<>();

    // CONNECT GAMES TO LEAGUE
    @OneToMany(mappedBy = "league")
    @JsonManagedReference("league-games")
    private List<Game> games = new ArrayList<>();
}