package com.example.cms.controller;

import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Player;
import com.example.cms.model.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {

    private final PlayerRepository repository;

    public PlayerController(PlayerRepository repository) {
        this.repository = repository;
    }

    // FIND ALL PLAYERS
    @GetMapping
    List<Player> retrieveAllPlayers() {
        return repository.findAll();
    }

    // RETRIEVE PLAYER
    @GetMapping("/{playerId}")
    public ResponseEntity<Player> retrievePlayer(@PathVariable Long playerId) {
        return repository.findById(playerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET CAPTAIN INFO
    @GetMapping("/getCaptainInfo/{teamId}")
    public ResponseEntity<?> retrieveCaptainInfo(@PathVariable("teamId") String teamId) {
        Optional<Map<String, Object>> captainInfo = repository.findCaptainInformationByTeamId(teamId);

        // Check to make sure that captain exists for the inputted teamId
        if (captainInfo.isPresent()) {
            Map<String, Object> response = new HashMap<>(captainInfo.get());
            response.put("teamID", teamId);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Captain not found for team ID: " + teamId);
        }
    }

    // GET TEAM SCHEDULE
    @GetMapping("/getSchedule/{teamId}")
    public ResponseEntity<List<Game>> retrieveTeamSchedule(@PathVariable("teamId") String teamId) {
        List<Game> games = repository.findTeamSchedule(teamId);
        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }
}