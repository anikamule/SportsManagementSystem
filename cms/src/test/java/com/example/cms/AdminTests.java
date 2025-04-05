package com.example.cms;

import com.example.cms.controller.AdminController;
import com.example.cms.model.entity.Admin;
import com.example.cms.model.entity.Captain;
import com.example.cms.model.entity.Referee;
import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.League;
import com.example.cms.model.entity.Game;
import com.example.cms.model.repository.AdminRepository;
import com.example.cms.model.repository.CaptainRepository;
import com.example.cms.model.repository.RefereeRepository;
import com.example.cms.model.repository.TeamRepository;
import com.example.cms.model.repository.LeagueRepository;
import com.example.cms.model.repository.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CaptainRepository captainRepository;

    @Autowired
    private RefereeRepository refereeRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    @Autowired
    private GameRepository gameRepository;

    @Test
    void retrieveAllAdmins() throws Exception {
        // Test retrieving all admins
        mockMvc.perform(get("/admin")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2)); // Based on data.sql, expecting 2 admins
    }

    @Test
    void createLeague() throws Exception {
        // Test creating a new league
        mockMvc.perform(post("/admin/league")
                        .param("leagueID", "L999")
                        .param("leagueName", "Test League")
                        .param("leagueGender", "Mixed")
                        .param("divisionNum", "Division3")
                        .param("leagueSport", "Basketball")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("League created successfully."));

        // Verify the league was created
        assertTrue(leagueRepository.findById("L999").isPresent());
        League createdLeague = leagueRepository.findById("L999").get();
        assertEquals("Test League", createdLeague.getLeagueName());
        assertEquals("Mixed", createdLeague.getLeagueGender());
        assertEquals("Division3", createdLeague.getDivisionNum());
        assertEquals("Basketball", createdLeague.getLeagueSport());
    }

    @Test
    void createTeam() throws Exception {
        // Test creating a new team
        // First, ensure there's a league and captain to assign to
        if (!leagueRepository.findById("L001").isPresent()) {
            // Create a league if it doesn't exist
            mockMvc.perform(post("/admin/league")
                    .param("leagueID", "L001")
                    .param("leagueName", "League for Team Test")
                    .param("leagueGender", "Mens")
                    .param("divisionNum", "Division1")
                    .param("leagueSport", "Soccer")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Create team
        mockMvc.perform(post("/admin/team")
                        .param("teamID", "T999")
                        .param("teamName", "Test Team")
                        .param("captainID", "1") // Using existing captain with ID 1
                        .param("leagueID", "L001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Team created successfully."));

        // Verify the team was created
        assertTrue(teamRepository.findById("T999").isPresent());
        Team createdTeam = teamRepository.findById("T999").get();
        assertEquals("Test Team", createdTeam.getTeamName());
        assertEquals(1L, createdTeam.getCaptain().getUserID());
        assertEquals("L001", createdTeam.getLeague().getLeagueID());
    }

    @Test
    void deleteTeam() throws Exception {
        // First create a team to delete
        if (!teamRepository.findById("T888").isPresent()) {
            // Create league if needed
            if (!leagueRepository.findById("L001").isPresent()) {
                mockMvc.perform(post("/admin/league")
                        .param("leagueID", "L001")
                        .param("leagueName", "League for Team Test")
                        .param("leagueGender", "Mens")
                        .param("divisionNum", "Division1")
                        .param("leagueSport", "Soccer")
                        .contentType(MediaType.APPLICATION_JSON));
            }

            // Create team
            mockMvc.perform(post("/admin/team")
                    .param("teamID", "T888")
                    .param("teamName", "Team To Delete")
                    .param("captainID", "1") // Using existing captain with ID 1
                    .param("leagueID", "L001")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test deleting the team
        mockMvc.perform(delete("/admin/team/T888")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Team deleted successfully."));

        // Verify the team was deleted
        assertFalse(teamRepository.findById("T888").isPresent());
    }

    @Test
    void createGame() throws Exception {
        // Test creating a new game
        // First, ensure there are teams and a referee for the game
        if (!teamRepository.findById("T001").isPresent() || !teamRepository.findById("T002").isPresent()) {
            // Create teams if needed (assuming league L001 exists from data.sql)
            fail("Required teams T001 and T002 do not exist in database");
        }

        // Format date correctly
        LocalDateTime gameTime = LocalDateTime.now().plusDays(7);
        String formattedDateTime = gameTime.format(DateTimeFormatter.ISO_DATE_TIME);

        // Create game
        mockMvc.perform(post("/admin/game")
                        .param("gameID", "999")
                        .param("datetime", formattedDateTime)
                        .param("location", "Test Stadium")
                        .param("team1ID", "T001")
                        .param("team2ID", "T002")
                        .param("teamScore1", "0")
                        .param("teamScore2", "0")
                        .param("gameStatus", "upcoming")
                        .param("refereeID", "11") // Using existing referee with ID 11
                        .param("leagueID", "L001")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Game created successfully."));

        // Verify the game was created
        assertTrue(gameRepository.findById(999L).isPresent());
        Game createdGame = gameRepository.findById(999L).get();
        assertEquals("Test Stadium", createdGame.getLocation());
        assertEquals("T001", createdGame.getTeam1().getTeamID());
        assertEquals("T002", createdGame.getTeam2().getTeamID());
        assertEquals("upcoming", createdGame.getGameStatus());
    }

    @Test
    void deleteGame() throws Exception {
        // First create a game to delete if it doesn't exist
        if (!gameRepository.findById(888L).isPresent()) {
            // Format date correctly
            LocalDateTime gameTime = LocalDateTime.now().plusDays(7);
            String formattedDateTime = gameTime.format(DateTimeFormatter.ISO_DATE_TIME);

            // Create game
            mockMvc.perform(post("/admin/game")
                    .param("gameID", "888")
                    .param("datetime", formattedDateTime)
                    .param("location", "Stadium To Delete")
                    .param("team1ID", "T001")
                    .param("team2ID", "T002")
                    .param("teamScore1", "0")
                    .param("teamScore2", "0")
                    .param("gameStatus", "upcoming")
                    .param("refereeID", "11") // Using existing referee with ID 11
                    .param("leagueID", "L001")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test deleting the game
        mockMvc.perform(delete("/admin/game/888")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Game deleted successfully."));

        // Verify the game was deleted
        assertFalse(gameRepository.findById(888L).isPresent());
    }

    @Test
    void assignCaptainToTeam() throws Exception {
        // First create a team without a captain if it doesn't exist
        if (!teamRepository.findById("T777").isPresent()) {
            // Create team without captain - don't include captainID parameter at all
            mockMvc.perform(post("/admin/team")
                    .param("teamID", "T777")
                    .param("teamName", "Team For Captain Assignment")
                    .param("leagueID", "L001")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test assigning a captain
        mockMvc.perform(put("/admin/team/T777/captain/2")
                        .param("adminId", "6")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Captain assigned successfully."));

        // Verify the captain was assigned
        Team team = teamRepository.findById("T777").orElse(null);
        assertNotNull(team, "Team should exist after assignment");
        assertNotNull(team.getCaptain(), "Captain should not be null after assignment");
        assertEquals(2L, team.getCaptain().getUserID(), "Captain ID should match assigned ID");
    }

    @Test
    void removeCaptainFromTeam() throws Exception {
        // First create a team with a captain
        if (!teamRepository.findById("T666").isPresent()) {
            // Create team with captain
            mockMvc.perform(post("/admin/team")
                    .param("teamID", "T666")
                    .param("teamName", "Team For Captain Removal")
                    .param("captainID", "3") // Assign captain initially
                    .param("leagueID", "L001")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test removing the captain
        mockMvc.perform(delete("/admin/team/T666/captain")
                        .param("adminId", "6") // Using existing admin ID
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Captain removed successfully."));

        // Verify the captain was removed
        Team team = teamRepository.findById("T666").orElse(null);
        assertNotNull(team);
        assertNull(team.getCaptain());
    }

    @Test
    void updateGameScore() throws Exception {
        // First create a game if it doesn't exist
        if (!gameRepository.findById(777L).isPresent()) {
            // Format date correctly
            LocalDateTime gameTime = LocalDateTime.now().plusDays(7);
            String formattedDateTime = gameTime.format(DateTimeFormatter.ISO_DATE_TIME);

            // Create game
            mockMvc.perform(post("/admin/game")
                    .param("gameID", "777")
                    .param("datetime", formattedDateTime)
                    .param("location", "Score Update Stadium")
                    .param("team1ID", "T001")
                    .param("team2ID", "T002")
                    .param("teamScore1", "0")
                    .param("teamScore2", "0")
                    .param("gameStatus", "upcoming")
                    .param("refereeID", "11")
                    .param("leagueID", "L001")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test updating the game score
        mockMvc.perform(put("/admin/game/777/score")
                        .param("teamScore1", "3")
                        .param("teamScore2", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Game score updated successfully."));

        // Verify the score was updated
        Game game = gameRepository.findById(777L).orElse(null);
        assertNotNull(game);
        assertEquals(3, game.getTeamScore1());
        assertEquals(2, game.getTeamScore2());
    }

    @Test
    void updateGameStatus() throws Exception {
        // First create a game if it doesn't exist
        if (!gameRepository.findById(666L).isPresent()) {
            // Format date correctly
            LocalDateTime gameTime = LocalDateTime.now().plusDays(7);
            String formattedDateTime = gameTime.format(DateTimeFormatter.ISO_DATE_TIME);

            // Create game
            mockMvc.perform(post("/admin/game")
                    .param("gameID", "666")
                    .param("datetime", formattedDateTime)
                    .param("location", "Status Update Stadium")
                    .param("team1ID", "T001")
                    .param("team2ID", "T002")
                    .param("teamScore1", "0")
                    .param("teamScore2", "0")
                    .param("gameStatus", "upcoming")
                    .param("refereeID", "11")
                    .param("leagueID", "L001")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test updating the game status
        mockMvc.perform(put("/admin/game/666/status")
                        .param("gameStatus", "completed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Game status updated successfully."));

        // Verify the status was updated
        Game game = gameRepository.findById(666L).orElse(null);
        assertNotNull(game);
        assertEquals("completed", game.getGameStatus());
    }

    @Test
    void createCaptain() throws Exception {
        // Test creating a new captain
        mockMvc.perform(post("/admin/captain")
                        .param("userID", "50")
                        .param("firstName", "New")
                        .param("lastName", "Captain")
                        .param("email", "new.captain@example.com")
                        .param("role", "captain")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Captain created successfully."));

        // Verify the captain was created
        assertTrue(captainRepository.findById(50L).isPresent());
        Captain createdCaptain = captainRepository.findById(50L).get();
        assertEquals("New", createdCaptain.getFirstName());
        assertEquals("Captain", createdCaptain.getLastName());
        assertEquals("new.captain@example.com", createdCaptain.getEmail());
        assertEquals("captain", createdCaptain.getRole());
    }

    @Test
    void deleteCaptain() throws Exception {
        // First create a captain to delete
        if (!captainRepository.findById(40L).isPresent()) {
            // Create captain
            mockMvc.perform(post("/admin/captain")
                    .param("userID", "40")
                    .param("firstName", "Delete")
                    .param("lastName", "Captain")
                    .param("email", "delete.captain@example.com")
                    .param("role", "captain")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test deleting the captain
        mockMvc.perform(delete("/admin/deleteCaptain/40")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Captain deleted successfully!"));

        // Verify the captain was deleted
        assertFalse(captainRepository.findById(40L).isPresent());
    }

    @Test
    void deleteNonExistentCaptain() throws Exception {
        // Test deleting a captain that doesn't exist
        mockMvc.perform(delete("/admin/deleteCaptain/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Captain not found."));
    }

    @Test
    void deleteLeagueWithTeamsAndGames() throws Exception {
        // First create a league with teams and games
        if (!leagueRepository.findById("L555").isPresent()) {
            // Create league
            mockMvc.perform(post("/admin/league")
                    .param("leagueID", "L555")
                    .param("leagueName", "League To Delete")
                    .param("leagueGender", "Mixed")
                    .param("divisionNum", "Division4")
                    .param("leagueSport", "Hockey")
                    .contentType(MediaType.APPLICATION_JSON));

            // Create team in this league
            mockMvc.perform(post("/admin/team")
                    .param("teamID", "T555")
                    .param("teamName", "Team In League To Delete")
                    .param("captainID", "1") // Using existing captain
                    .param("leagueID", "L555")
                    .contentType(MediaType.APPLICATION_JSON));

            // Create another team in this league
            mockMvc.perform(post("/admin/team")
                    .param("teamID", "T556")
                    .param("teamName", "Another Team In League To Delete")
                    .param("captainID", "2") // Using existing captain
                    .param("leagueID", "L555")
                    .contentType(MediaType.APPLICATION_JSON));

            // Create game in this league
            LocalDateTime gameTime = LocalDateTime.now().plusDays(7);
            String formattedDateTime = gameTime.format(DateTimeFormatter.ISO_DATE_TIME);
            mockMvc.perform(post("/admin/game")
                    .param("gameID", "555")
                    .param("datetime", formattedDateTime)
                    .param("location", "League Delete Stadium")
                    .param("team1ID", "T555")
                    .param("team2ID", "T556")
                    .param("teamScore1", "0")
                    .param("teamScore2", "0")
                    .param("gameStatus", "upcoming")
                    .param("refereeID", "11")
                    .param("leagueID", "L555")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test deleting the league (which should also delete associated teams and games)
        mockMvc.perform(delete("/admin/league/L555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("League, its games, and teams deleted successfully."));

        // Verify the league was deleted
        assertFalse(leagueRepository.findById("L555").isPresent());

        // Verify teams in the league were deleted
        assertFalse(teamRepository.findById("T555").isPresent());
        assertFalse(teamRepository.findById("T556").isPresent());

        // Verify games in the league were deleted
        assertFalse(gameRepository.findById(555L).isPresent());
    }

    @Test
    void assignRefereeToGame() throws Exception {
        // First create a game if it doesn't exist
        if (!gameRepository.findById(444L).isPresent()) {
            // Format date correctly
            LocalDateTime gameTime = LocalDateTime.now().plusDays(7);
            String formattedDateTime = gameTime.format(DateTimeFormatter.ISO_DATE_TIME);

            // Create game
            mockMvc.perform(post("/admin/game")
                    .param("gameID", "444")
                    .param("datetime", formattedDateTime)
                    .param("location", "Referee Assignment Stadium")
                    .param("team1ID", "T001")
                    .param("team2ID", "T002")
                    .param("teamScore1", "0")
                    .param("teamScore2", "0")
                    .param("gameStatus", "upcoming")
                    .param("refereeID", "11") // Initial referee
                    .param("leagueID", "L001")
                    .contentType(MediaType.APPLICATION_JSON));
        }

        // Test assigning a different referee
        mockMvc.perform(post("/admin/game/444/referee")
                        .param("refereeID", "12") // Change to referee with ID 12
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Referee assigned successfully."));

        // Verify the referee was changed
        Game game = gameRepository.findById(444L).orElse(null);
        assertNotNull(game);
        assertEquals(12L, game.getReferee().getUserID());
    }
}