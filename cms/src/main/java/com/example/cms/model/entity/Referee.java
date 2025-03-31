package com.example.cms.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "referees")

public class Referee extends User{

    // A referee can have many games but a game can only have one referee
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "referee")
    @Nullable
    @JsonManagedReference("referee-games")
    private List<Game> games = new ArrayList<>();

}


