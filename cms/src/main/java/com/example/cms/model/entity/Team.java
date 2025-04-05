package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "teams")
public class Team {

    @Id
    private String teamID;

    @NotEmpty
    private String teamName;

    @NotEmpty
    @Nullable
    private String ranking;

    // CONNECT PLAYERS TO TEAM
    @OneToMany(mappedBy = "team") // one team can have many players
    @Nullable
    @JsonManagedReference("team-players") // Add a name to distinguish different relationships
    private List<Player> players = new ArrayList<>();

    // CONNECT CAPTAIN TO TEAM
    @Nullable
    @OneToOne
    @JoinColumn(name = "captainID", nullable = true) // Foreign key reference to the captain
    @JsonManagedReference("team-captain") // Add a name to distinguish different relationships
    private Captain captain;

    // CONNECT TEAM TO LEAGUE
    @ManyToOne
    @JoinColumn(name = "leagueID")
    @JsonBackReference("league-teams") // Team is the child in this relationship
    private League league;

    // HOME GAMES (TEAM 1)
    @OneToMany(mappedBy = "team1")
    @JsonManagedReference("team1-games") // Add a name to distinguish different relationships
    private List<Game> homeGames = new ArrayList<>();

    // AWAY GAME (TEAM 2)
    @OneToMany(mappedBy = "team2")
    @JsonManagedReference("team2-games") // Add a name to distinguish different relationships
    private List<Game> awayGames = new ArrayList<>();

    public Team(String teamID, String teamName, Captain captain, League league){
        this.teamID = teamID;
        this.teamName = teamName;
        this.league = league;
        this.captain = captain;
    }
}