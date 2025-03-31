package com.example.cms.controller;


import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Player;
import com.example.cms.model.entity.Referee;
import com.example.cms.model.repository.PlayerRepository;
import com.example.cms.model.repository.RefereeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;


@RestController
@RequestMapping("/referees")
public class RefereeController {

    private final RefereeRepository refereeRepository;

    public RefereeController(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }

    // FIND ALL PLAYERS
    @GetMapping
    List<Referee> retrieveAllPlayers() {
        return refereeRepository.findAll();
    }

    // Get all games
//    @GetMapping("/games")
//    public ResponseEntity<List<Game>> getAllGames() {
//        List<Game> games = refereeRepository.getAllGames();
//        if (games.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(games);
//    }
//
//
//    // Get games assigned to a specific referee
//    @GetMapping("/{refereeId}/games")
//    public ResponseEntity<List<Game>> getGamesAssignedToReferee(@PathVariable("refereeId") Long refereeId) {
//        List<Game> games = refereeRepository.getGamesAssigned(refereeId);
//        if (games.isEmpty()) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(games);
//    }
//
//
//    // Assign a referee to a game
//    @PutMapping("/assign/{gameId}/{refereeId}")
//    public ResponseEntity<String> assignRefereeToGame(@PathVariable("gameId") Long gameId,
//                                                      @PathVariable("refereeId") Long refereeId) {
//        int result = refereeRepository.assignRefereeToGame(gameId, refereeId);
//        if (result > 0) {
//            return ResponseEntity.ok("Assigning referee is successfully done.");
//        }
//        return ResponseEntity.badRequest().body("Assigning referee to game has failed.");
//    }
//
//
//    // Update game status to 'Finished' for a specific game
//    @PutMapping("/games/{gameId}/finish")
//    public ResponseEntity<String> finishGame(@PathVariable("gameId") Long gameId) {
//        int result = refereeRepository.setGameStatusToFinished(gameId);
//        if (result > 0) {
//            return ResponseEntity.ok("Game status updated to 'Finished'.");
//        }
//        return ResponseEntity.badRequest().body("Updating game status has failed.");
//    }


    // Update game score by a specific referee
//    @PutMapping("/games/{gameId}/{refereeId}/score")
//    public ResponseEntity<String> updateGameScore(@PathVariable("gameId") Long gameId,
//                                                  @PathVariable("refereeId") Long refereeId,
//                                                  @RequestBody int[] score) {
//        int result = refereeRepository.updateGameScore(gameId, refereeId, score);
//        if (result > 0) {
//            return ResponseEntity.ok("Updating game status is successfully done.");
//        }
//        return ResponseEntity.badRequest().body("Updating game score has failed.");
//    }


    // Update game score and status (if score is updated, status changes to 'Completed')
//    @PutMapping("/games/{gameId}/{refereeId}/score/status")
//    public ResponseEntity<String> updateGameScoreAndStatus(@PathVariable("gameId") Long gameId,
//                                                           @PathVariable("refereeId") Long refereeId,
//                                                           @RequestBody int[] score) {
//        int result = refereeRepository.updateGameScoreAndStatus(gameId, refereeId, score);
//        if (result > 0) {
//            return ResponseEntity.ok("Game score and status updated successfully.");
//        }
//        return ResponseEntity.badRequest().body("Updating game score and status have failed.");
//    }
}
