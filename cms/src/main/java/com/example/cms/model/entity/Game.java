package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "games")
public class Game {

    @Id
    private long gameID;

    @DateTimeFormat
    private LocalDateTime datetime;

    @NotEmpty
    private String location;

    @ManyToOne
    @JsonBackReference("team1-games")
    @JoinColumn(name = "team1ID", nullable = false)
    private Team team1;

    @ManyToOne
    @JsonBackReference("team2-games")
    @JoinColumn(name = "team2ID", nullable = false)
    private Team team2;

    @Nullable
    private int teamScore1;

    @Nullable
    private int teamScore2;

    @NotEmpty
    private String gameStatus;
    // upcoming, completed

    @ManyToOne
    @Nullable
    @JsonBackReference("referee-games")
    @JoinColumn(name = "refereeID")
    private Referee referee;

    @ManyToOne
    @JoinColumn(name = "leagueID")
    @JsonBackReference("league-games")
    private League league;

}
