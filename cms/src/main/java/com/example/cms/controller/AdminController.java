package com.example.cms.controller;

import com.example.cms.controller.dto.LeagueDTO;
import com.example.cms.model.entity.Admin;
import com.example.cms.model.repository.AdminRepository;
import com.example.cms.model.repository.CaptainRepository;
import com.example.cms.model.repository.PlayerRepository;
import com.example.cms.model.repository.RefereeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/admin")
class AdminController {

    @Autowired
    private final AdminRepository adminRepository;

    @Autowired
    private RefereeRepository refereeRepository;

    @Autowired
    private CaptainRepository captainRepository;

    @Autowired
    AdminController(AdminRepository adminRepository, RefereeRepository refereeRepository, CaptainRepository captainRepository) {
        this.adminRepository = adminRepository;
        this.refereeRepository = refereeRepository;
        this.captainRepository = captainRepository;
    }

    // Get all admins
    @GetMapping()
    List<Admin> retrieveAllAdmin() {
        return adminRepository.findAll();
    }

    // Create a new league
    @PostMapping("/league")
    String createLeague(@RequestParam String leagueID,
                        @RequestParam String leagueName,
                        @RequestParam String leagueGender,
                        @RequestParam String divisionNum,
                        @RequestParam String leagueSport) {
        try {
            adminRepository.createLeague(
                    leagueID,
                    leagueName,
                    leagueGender,
                    divisionNum,
                    leagueSport
            );
            return "League created successfully.";
        } catch (Exception e) {
            return "Error creating league: " + e.getMessage();
        }
    }

    // Create a new team
    @PostMapping("/team")
    String createTeam(@RequestParam String teamID,
                      @RequestParam String teamName,
                      @RequestParam String captainID,
                      @RequestParam String leagueID) {
        try {
            adminRepository.createTeam(teamID, teamName, captainID, leagueID);
            return "Team created successfully.";
        } catch (Exception e) {
            return "Error creating team: " + e.getMessage();
        }
    }

    // Delete a team
    @DeleteMapping("/team/{teamId}")
    String deleteTeam(@PathVariable String teamId) {
        try {
            adminRepository.deleteTeam(teamId);
            return "Team deleted successfully.";
        } catch (Exception e) {
            return "Error deleting team: " + e.getMessage();
        }
    }

    // Create a new game
    @PostMapping("/game")
    String createGame(@RequestParam Long gameID,
                      @RequestParam String datetime,
                      @RequestParam String location,
                      @RequestParam String team1ID,
                      @RequestParam String team2ID,
                      @RequestParam int teamScore1,
                      @RequestParam int teamScore2,
                      @RequestParam String gameStatus,
                      @RequestParam Long refereeID,
                      @RequestParam String leagueID) {
        try {
            // Parse the datetime string to LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME; // Or your specific format
            LocalDateTime parsedDateTime = LocalDateTime.parse(datetime, formatter);

            // Format LocalDateTime back to String if needed for your repository
            String formattedDateTime = parsedDateTime.toString(); // Or use a specific pattern if necessary

            adminRepository.createGame(
                    gameID, formattedDateTime, location, team1ID, team2ID,
                    teamScore1, teamScore2, gameStatus, refereeID, leagueID
            );
            return "Game created successfully.";
        } catch (DateTimeParseException e) {
            System.err.println("Error parsing date/time: " + e.getMessage());
            return "Error parsing date/time: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("Error creating game: " + e.getMessage());
            return "Error creating game: " + e.getMessage();
        }
    }

    // Delete a game
    @DeleteMapping("/game/{gameId}")
    String deleteGame(@PathVariable Long gameId) {
        try {
            adminRepository.deleteGame(gameId);
            return "Game deleted successfully.";
        } catch (Exception e) {
            return "Error deleting game: " + e.getMessage();
        }
    }

    // Assign captain to a team
    @PutMapping("/team/{teamId}/captain/{captainId}")
    public String assignCaptain(
            @PathVariable String teamId,
            @PathVariable String captainId,  // Changed to String
            @RequestParam String adminId) {  // Changed to String
        try {
            adminRepository.assignCaptainByAdmin(teamId, captainId, adminId);
            return "Captain assigned successfully.";
        } catch (Exception e) {
            return "Error assigning captain: " + e.getMessage();
        }
    }

    // Assign a referee to a game
    @PostMapping("/game/{gameId}/referee")
    String assignReferee(@PathVariable Long gameId, @RequestParam Long refereeID) {
        try {
            adminRepository.assignReferee(gameId, refereeID);
            return "Referee assigned successfully.";
        } catch (Exception e) {
            return "Error assigning referee: " + e.getMessage();
        }
    }

    // Update game score
    @PutMapping("/game/{gameId}/score")
    String updateGameScore(@PathVariable Long gameId,
                           @RequestParam int teamScore1,
                           @RequestParam int teamScore2) {
        try {
            adminRepository.updateGameScore(gameId, teamScore1, teamScore2);
            return "Game score updated successfully.";
        } catch (Exception e) {
            return "Error updating game score: " + e.getMessage();
        }
    }

    // Update game status
    @PutMapping("/game/{gameId}/status")
    String updateGameStatus(@PathVariable Long gameId,
                            @RequestParam String gameStatus) {
        try {
            adminRepository.updateGameStatus(gameId, gameStatus);
            return "Game status updated successfully.";
        } catch (Exception e) {
            return "Error updating game status: " + e.getMessage();
        }
    }
    // Remove captain to a team
    @DeleteMapping("/team/{teamId}/captain")
    public String removeCaptain(
            @PathVariable String teamId,
            @RequestParam String adminId) {
        try {
            adminRepository.removeCaptainByAdmin(teamId, adminId);
            return "Captain removed successfully.";
        } catch (Exception e) {
            return "Error removing captain: " + e.getMessage();
        }
    }

    @PostMapping("/captain")
    String createTeam(@RequestParam String userID,
                      @RequestParam String firstName,
                      @RequestParam String lastName,
                      @RequestParam String email,
                      @RequestParam String role) {
        try {
            adminRepository.createCaptain(userID, firstName, lastName, email, role);
            return "Captain created successfully.";
        } catch (Exception e) {
            return "Error creating captain: " + e.getMessage();
        }
    }

    // NEW DELETE LEAGUE
    @DeleteMapping("/league/{leagueId}")
    public String deleteLeague(@PathVariable String leagueId) {
        try {
            adminRepository.deleteGamesByLeagueId(leagueId);  // Step 1: delete games
            adminRepository.deleteTeamsByLeagueId(leagueId);  // Step 2: delete teams
            adminRepository.deleteLeague(leagueId);           // Step 3: delete league
            return "League, its games, and teams deleted successfully.";
        } catch (Exception e) {
            return "Error deleting league: " + e.getMessage();
        }
    }

    // Delete referee
    @DeleteMapping("/deleteReferee/{refereeId}")
    @Transactional
    public ResponseEntity<String> deleteReferee(@PathVariable String refereeId) {
        boolean exists = refereeRepository.existsById(refereeId);
        if (!exists) {
            return ResponseEntity.status(404).body("Referee not found.");
        }

        refereeRepository.deleteById(refereeId);
        return ResponseEntity.ok("Referee deleted successfully!");
    }

    // Delete captain
    @DeleteMapping("/deleteCaptain/{captainId}")
    @Transactional
    public ResponseEntity<String> deleteCaptain(@PathVariable String captainId) {
        boolean exists = captainRepository.existsById(captainId);
        if (!exists) {
            return ResponseEntity.status(404).body("Captain not found.");
        }

        captainRepository.deleteById(captainId);
        return ResponseEntity.ok("Captain deleted successfully!");
    }





}