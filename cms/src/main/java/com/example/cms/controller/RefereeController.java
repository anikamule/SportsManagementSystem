package com.example.cms.controller;

import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Referee;
import com.example.cms.model.repository.RefereeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/referees")
public class RefereeController {

    private final RefereeRepository refereeRepository;

    public RefereeController(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    // FIND ALL REFEREES
    @GetMapping
    List<Referee> retrieveAllReferees() {
        return refereeRepository.findAll();
    }

    // Get all games
    @GetMapping("/games")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = refereeRepository.getAllGames();
        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    // Get games assigned to a specific referee
    @GetMapping("/{refereeId}/games")
    public ResponseEntity<List<Game>> getGamesAssignedToReferee(@PathVariable("refereeId") Long refereeId) {
        List<Game> games = refereeRepository.getGamesAssigned(refereeId);
        if (games.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(games);
    }

    // Assign a referee to a game
    @PutMapping("/assign/{gameId}/{refereeId}")
    public ResponseEntity<String> assignRefereeToGame(@PathVariable("gameId") Long gameId,
                                                      @PathVariable("refereeId") Long refereeId) {
        int result = refereeRepository.assignRefereeToGame(gameId, refereeId);
        if (result > 0) {
            return ResponseEntity.ok("Assigning referee is successfully done.");
        }
        return ResponseEntity.badRequest().body("Assigning referee to game has failed.");
    }

    // Update game status to 'Completed' for a specific game
    @PutMapping("/games/{gameId}/complete")
    public ResponseEntity<String> completeGame(@PathVariable("gameId") Long gameId) {
        int result = refereeRepository.setGameStatusToCompleted(gameId);
        if (result > 0) {
            return ResponseEntity.ok("Game status updated to 'Completed'.");
        }
        return ResponseEntity.badRequest().body("Updating game status has failed.");
    }

    // Update game score by a specific referee
    @PutMapping("/games/{gameId}/{refereeId}/score")
    public ResponseEntity<String> updateGameScore(@PathVariable("gameId") Long gameId,
                                                  @PathVariable("refereeId") Long refereeId,
                                                  @RequestBody Map<String, Integer> scoreMap) {
        Integer teamScore1 = scoreMap.get("teamScore1");
        Integer teamScore2 = scoreMap.get("teamScore2");

        if (teamScore1 == null || teamScore2 == null) {
            return ResponseEntity.badRequest().body("Both team scores must be provided.");
        }

        int result = refereeRepository.updateGameScore(gameId, refereeId, teamScore1, teamScore2);
        if (result > 0) {
            return ResponseEntity.ok("Game score updated successfully.");
        }
        return ResponseEntity.badRequest().body("Updating game score has failed.");
    }

    // Update game score and status (if score is updated, status changes to 'Completed')
    @PutMapping("/games/{gameId}/{refereeId}/score/status")
    public ResponseEntity<String> updateGameScoreAndStatus(@PathVariable("gameId") Long gameId,
                                                           @PathVariable("refereeId") Long refereeId,
                                                           @RequestBody Map<String, Integer> scoreMap) {
        Integer teamScore1 = scoreMap.get("teamScore1");
        Integer teamScore2 = scoreMap.get("teamScore2");

        if (teamScore1 == null || teamScore2 == null) {
            return ResponseEntity.badRequest().body("Both team scores must be provided.");
        }

        int result = refereeRepository.updateGameScoreAndStatus(gameId, refereeId, teamScore1, teamScore2);
        if (result > 0) {
            return ResponseEntity.ok("Game score and status updated successfully.");
        }
        return ResponseEntity.badRequest().body("Updating game score and status have failed.");
    }
}