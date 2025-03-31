package com.example.cms.controller;

import com.example.cms.controller.exceptions.PlayerNotFoundException;
import com.example.cms.model.entity.Captain;
import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Player;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.PlayerRepository;
import com.example.cms.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//    @GetMapping("/players/{playerId}")
//    Player retreivePlayer(@PathVariable("playerId") Long userID){
//        return repository.findById(userID)
//                .orElseThrow(() -> new PlayerNotFoundException(userID));
//    }
//
//    // CONSIDER MAKING IT SO THAT USER CAN UPDATE THEIR INFORMATION
//    // (firstName, lastName, email, password)
//
//    // GET CAPTAIN INFO
//    @GetMapping("/players/getCaptainInfo/{teamId}")
//    public ResponseEntity<?> retrieveCaptainInfo(@PathVariable("teamId") String teamId) {
//        Optional<Map<String, String>> captainInfo = repository.findCaptainInformationByTeamId(teamId);
//
//        // Check to make sure that captain exists for the inputted teamId
//        if (captainInfo.isPresent()) {
//            return ResponseEntity.ok(captainInfo.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Captain not found for team ID: " + teamId);
//        }
//    }
//
//    // GET TEAM SCHEDULE
//    @GetMapping("/players/getSchedule/{teamId}")
//    public ResponseEntity<List<Game>> retrieveTeamSchedule(@PathVariable("teamId") String teamId) {
//        List<Game> games = repository.findTeamSchedule(teamId);
//        return ResponseEntity.ok(games);
//        // ADD EXCEPTION WHEN no games are found for team ID
//    }
}
