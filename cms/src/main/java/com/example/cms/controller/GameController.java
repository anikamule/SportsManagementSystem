package com.example.cms.controller;

import com.example.cms.model.entity.Game;
import com.example.cms.model.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games") // Add a base path for all game-related endpoints
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    // Get all games
    @GetMapping
    public List<Game> retreiveAllGames() {
        return gameRepository.findAll();
    }

    // Get game by ID
    @GetMapping("/{gameID}")
    public ResponseEntity<Game> getGameById(@PathVariable Long gameID) {
        Game game = gameRepository.findGameById(gameID);
        return game != null ? ResponseEntity.ok(game) : ResponseEntity.notFound().build();
    }

    // Get games by league ID
    @GetMapping("/league/{leagueID}")
    public List<Game> getGamesByLeagueId(@PathVariable Long leagueID) {
        return gameRepository.findGamesByLeagueId(leagueID);
    }

    // Get games by team ID
    @GetMapping("/team/{teamID}")
    public List<Game> getGamesByTeamId(@PathVariable Long teamID) {
        return gameRepository.findGamesByTeamId(teamID);
    }

    // Get game details (date, time, location)
    @GetMapping("/{gameID}/details")
    public ResponseEntity<?> getGameDetails(@PathVariable Long gameID) {
        Object[] details = gameRepository.findGameDetailsById(gameID);
        if (details != null) {
            return ResponseEntity.ok(new GameDetailsResponse(details[0].toString(), details[1].toString()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Inner class to format game details response
    private static class GameDetailsResponse {
        public String dateTime;
        public String location;

        public GameDetailsResponse(String dateTime, String location) {
            this.dateTime = dateTime;
            this.location = location;

            //Seu Add Comment See if it works

        }
    }
}