package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "players")
public class Player extends User{


    @ManyToOne // allow multiple players per team
    @Nullable
    @JoinColumn(name = "teamID") // FK
    @JsonBackReference("team-players")
    private Team team;

//    @ManyToOne
//    @JoinColumn(name = "team_id") // <- must match this
//    @JsonBackReference("team-players")
//    private Team team;
}

