package com.example.cms;

import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Referee;
import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.League;
import com.example.cms.model.entity.Captain;
import com.example.cms.model.repository.RefereeRepository;
import com.example.cms.model.repository.TeamRepository;
import com.example.cms.model.repository.LeagueRepository;
import com.example.cms.model.repository.GameRepository;
import com.example.cms.model.repository.CaptainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RefereeTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RefereeRepository refereeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CaptainRepository captainRepository;

    @Test
    void retrieveAllReferees() throws Exception {
        // Test retrieving all referees
        mockMvc.perform(get("/referees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3)); // Based on data.sql, expecting 3 referees
    }

    @Test
    void getAllGames() throws Exception {
        // Test retrieving all games
        mockMvc.perform(get("/referees/games")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(8))); // Based on data.sql, expecting at least 8 games
    }

    @Test
    void getGamesAssignedToReferee() throws Exception {
        // Using existing referee ID from data.sql
        Long refereeId = 11L;

        // Perform the test
        mockMvc.perform(get("/referees/{refereeId}/games", refereeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(3))); // Based on data.sql, referee 11 has at least 3 games
    }

    @Test
    void assignRefereeToGame() throws Exception {
        // Use existing game from data.sql
        Long gameId = 7L; // Game ID 7 from data.sql has referee 13 assigned
        Long newRefereeId = 11L; // Assign referee 11 instead

        // Test assigning referee to game
        mockMvc.perform(put("/referees/assign/{gameId}/{refereeId}", gameId, newRefereeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Assigning referee is successfully done."));

        // Verify the referee was assigned
        Game game = gameRepository.findById(gameId).orElse(null);
        assertNotNull(game);
        assertNotNull(game.getReferee());
        assertEquals(newRefereeId, game.getReferee().getUserID());
    }

    @Test
    void completeGame() throws Exception {
        // Use existing game from data.sql with 'upcoming' status
        Long gameId = 8L; // Game ID 8 from data.sql

        // Test updating game status to completed
        mockMvc.perform(put("/referees/games/{gameId}/complete", gameId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Game status updated to 'Completed'."));

        // Verify the game status was updated
        Game game = gameRepository.findById(gameId).orElse(null);
        assertNotNull(game);
        assertEquals("Completed", game.getGameStatus());
    }

    @Test
    void updateGameScore() throws Exception {
        // Use existing game from data.sql that has an 'upcoming' status and a referee assigned
        Long gameId = 5L; // Game ID 5 from data.sql
        Long refereeId = 12L; // Referee assigned to game 5

        // Create score payload
        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("teamScore1", 3);
        scoreMap.put("teamScore2", 2);
        String scoreJson = objectMapper.writeValueAsString(scoreMap);

        // Test updating game score
        mockMvc.perform(put("/referees/games/{gameId}/{refereeId}/score", gameId, refereeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoreJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Game score updated successfully."));

        // Verify the score was updated
        Game game = gameRepository.findById(gameId).orElse(null);
        assertNotNull(game);
        assertEquals(3, game.getTeamScore1());
        assertEquals(2, game.getTeamScore2());
    }

    @Test
    void updateGameScoreAndStatus() throws Exception {
        // Use existing game from data.sql that has an 'upcoming' status and a referee assigned
        Long gameId = 6L; // Game ID 6 from data.sql
        Long refereeId = 12L; // Referee assigned to game 6

        // Create score payload
        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("teamScore1", 5);
        scoreMap.put("teamScore2", 3);
        String scoreJson = objectMapper.writeValueAsString(scoreMap);

        // Test updating game score and status
        mockMvc.perform(put("/referees/games/{gameId}/{refereeId}/score/status", gameId, refereeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoreJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Game score and status updated successfully."));

        // Verify the score and status were updated
        Game game = gameRepository.findById(gameId).orElse(null);
        assertNotNull(game);
        assertEquals(5, game.getTeamScore1());
        assertEquals(3, game.getTeamScore2());
        assertEquals("Completed", game.getGameStatus());
    }

    @Test
    void updateGameScoreInvalidReferee() throws Exception {
        // Use existing game with referee 11 assigned
        Long gameId = 1L; // Game ID 1 from data.sql with referee 11
        Long correctRefereeId = 11L; // Correct referee for this game
        Long incorrectRefereeId = 12L; // Incorrect referee for this game

        // Get initial values
        Game initialGame = gameRepository.findById(gameId).orElse(null);
        assertNotNull(initialGame);
        int initialScore1 = initialGame.getTeamScore1();
        int initialScore2 = initialGame.getTeamScore2();

        // Create score payload
        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("teamScore1", 9);
        scoreMap.put("teamScore2", 9);
        String scoreJson = objectMapper.writeValueAsString(scoreMap);

        // Test updating game score with non-assigned referee
        mockMvc.perform(put("/referees/games/{gameId}/{refereeId}/score", gameId, incorrectRefereeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoreJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Updating game score has failed."));

        // Verify the score was not updated
        Game game = gameRepository.findById(gameId).orElse(null);
        assertNotNull(game);
        assertEquals(initialScore1, game.getTeamScore1());
        assertEquals(initialScore2, game.getTeamScore2());
    }

    @Test
    void updateGameScoreMissingTeamScore() throws Exception {
        // Use existing game from data.sql
        Long gameId = 3L; // Game ID 3 from data.sql
        Long refereeId = 11L; // Referee assigned to game 3

        // Get initial values
        Game initialGame = gameRepository.findById(gameId).orElse(null);
        assertNotNull(initialGame);
        int initialScore1 = initialGame.getTeamScore1();
        int initialScore2 = initialGame.getTeamScore2();

        // Create incomplete score payload (missing teamScore2)
        Map<String, Integer> scoreMap = new HashMap<>();
        scoreMap.put("teamScore1", 7);
        // teamScore2 is missing
        String scoreJson = objectMapper.writeValueAsString(scoreMap);

        // Test updating game score with incomplete data
        mockMvc.perform(put("/referees/games/{gameId}/{refereeId}/score", gameId, refereeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoreJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Both team scores must be provided."));

        // Verify the score was not updated
        Game game = gameRepository.findById(gameId).orElse(null);
        assertNotNull(game);
        assertEquals(initialScore1, game.getTeamScore1());
        assertEquals(initialScore2, game.getTeamScore2());
    }

    // Helper method to create a test game
    private void createTestGame(Long gameId, Long refereeId) throws Exception {
        // Make sure teams and league exist
        ensureTeamsAndLeagueExist();

        // Format date correctly
        LocalDateTime gameTime = LocalDateTime.now().plusDays(7);
        String formattedDateTime = gameTime.format(DateTimeFormatter.ISO_DATE_TIME);

        // Try to find existing game
        boolean gameExists = gameRepository.findById(gameId).isPresent();

        if (!gameExists) {
            try {
                // Create game
                mockMvc.perform(post("/admin/game")
                        .param("gameID", gameId.toString())
                        .param("datetime", formattedDateTime)
                        .param("location", "Test Stadium for Referee Tests")
                        .param("team1ID", "T001")
                        .param("team2ID", "T002")
                        .param("teamScore1", "0")
                        .param("teamScore2", "0")
                        .param("gameStatus", "upcoming")
                        .param("refereeID", refereeId.toString())
                        .param("leagueID", "L001")
                        .contentType(MediaType.APPLICATION_JSON));
            } catch (Exception e) {
                // If AdminController is not available in test context, create game directly
                if (!gameRepository.findById(gameId).isPresent()) {
                    Game game = new Game();
                    game.setGameID(gameId);
                    game.setDatetime(gameTime);
                    game.setLocation("Test Stadium for Referee Tests");
                    game.setTeam1(teamRepository.findById("T001").orElse(null));
                    game.setTeam2(teamRepository.findById("T002").orElse(null));
                    game.setTeamScore1(0);
                    game.setTeamScore2(0);
                    game.setGameStatus("upcoming");
                    game.setReferee(refereeRepository.findById(refereeId).orElse(null));
                    game.setLeague(leagueRepository.findById("L001").orElse(null));
                    gameRepository.save(game);
                }
            }
        }
    }

    // Helper method to ensure required teams and league exist
    private void ensureTeamsAndLeagueExist() throws Exception {
        try {
            // Check if league exists, if not create it
            if (!leagueRepository.findById("L001").isPresent()) {
                try {
                    mockMvc.perform(post("/admin/league")
                            .param("leagueID", "L001")
                            .param("leagueName", "Test League")
                            .param("leagueGender", "Mixed")
                            .param("divisionNum", "Division1")
                            .param("leagueSport", "Soccer")
                            .contentType(MediaType.APPLICATION_JSON));
                } catch (Exception e) {
                    // If AdminController is not available, create League directly
                    League league = new League();
                    league.setLeagueID("L001");
                    league.setLeagueName("Test League");
                    league.setLeagueGender("Mixed");
                    league.setDivisionNum("Division1");
                    league.setLeagueSport("Soccer");
                    leagueRepository.save(league);
                }
            }

            // Check if team 1 exists, if not create it
            if (!teamRepository.findById("T001").isPresent()) {
                try {
                    mockMvc.perform(post("/admin/team")
                            .param("teamID", "T001")
                            .param("teamName", "Test Team 1")
                            .param("captainID", "1") // Assuming captain ID 1 exists
                            .param("leagueID", "L001")
                            .contentType(MediaType.APPLICATION_JSON));
                } catch (Exception e) {
                    // Create team directly if necessary
                    Team team = new Team();
                    team.setTeamID("T001");
                    team.setTeamName("Test Team 1");

                    // Find captain and league
                    Captain captain = captainRepository.findById(1L).orElse(null);
                    League league = leagueRepository.findById("L001").orElse(null);

                    team.setCaptain(captain);
                    team.setLeague(league);
                    teamRepository.save(team);
                }
            }

            // Check if team 2 exists, if not create it
            if (!teamRepository.findById("T002").isPresent()) {
                try {
                    mockMvc.perform(post("/admin/team")
                            .param("teamID", "T002")
                            .param("teamName", "Test Team 2")
                            .param("captainID", "2") // Assuming captain ID 2 exists
                            .param("leagueID", "L001")
                            .contentType(MediaType.APPLICATION_JSON));
                } catch (Exception e) {
                    // Create team directly if necessary
                    Team team = new Team();
                    team.setTeamID("T002");
                    team.setTeamName("Test Team 2");

                    // Find captain and league
                    Captain captain = captainRepository.findById(2L).orElse(null);
                    League league = leagueRepository.findById("L001").orElse(null);

                    team.setCaptain(captain);
                    team.setLeague(league);
                    teamRepository.save(team);
                }
            }
        } catch (Exception e) {
            // Log exception but continue - we'll use existing data if available
            System.err.println("Error setting up test data: " + e.getMessage());
        }
    }
}