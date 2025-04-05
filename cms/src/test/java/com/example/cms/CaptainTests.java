package com.example.cms;

import com.example.cms.controller.CaptainController;
import com.example.cms.controller.exceptions.CaptainNotFoundException;
import com.example.cms.model.entity.Captain;
import com.example.cms.model.entity.Player;
import com.example.cms.model.repository.CaptainRepository;
import com.example.cms.model.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CaptainTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CaptainRepository captainRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void retrieveAllCaptains() throws Exception {
        // Perform GET request to retrieve all captains
        mockMvc.perform(get("/captains")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5)); // Based on the data.sql, we expect 5 captains

        // Additional approach using response extraction
        MockHttpServletResponse response = mockMvc.perform(get("/captains")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert response status is 200 OK
        assertEquals(200, response.getStatus());

        // Convert response content to Captain array
        Captain[] captains = objectMapper.readValue(response.getContentAsString(), Captain[].class);

        // Assert the response contains the same number of captains as in the repository
        assertEquals(captainRepository.findAll().size(), captains.length);
        assertEquals(5, captains.length); // Based on the data.sql, we expect 5 captains
    }

    @Test
    void retrieveCaptainById() throws Exception {
        // Based on the data.sql, we know there's a captain with ID 1L

        // Using the jsonPath approach for more readable assertions
        mockMvc.perform(get("/captains/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(1))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        // Alternative approach with response extraction
        MockHttpServletResponse response = mockMvc.perform(get("/captains/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert response status is 200 OK
        assertEquals(200, response.getStatus());

        // Convert response content to Captain object
        Captain captain = objectMapper.readValue(response.getContentAsString(), Captain.class);

        // Assert the captain ID and other properties match what we expect
        assertEquals(1L, captain.getUserID());
        assertEquals("John", captain.getFirstName());
        assertEquals("Doe", captain.getLastName());
        assertEquals("john.doe@example.com", captain.getEmail());
    }

    @Test
    void retrieveNonExistentCaptain() throws Exception {
        // Test behavior when trying to retrieve a captain that doesn't exist
        mockMvc.perform(get("/captains/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void createAndAddPlayerToTeam() throws Exception {
        // Create a player JSON with fields that match the Player entity
        ObjectNode playerJson = objectMapper.createObjectNode();
        playerJson.put("userID", 123L);
        playerJson.put("firstName", "Test");
        playerJson.put("lastName", "Player");
        playerJson.put("email", "test.player@example.com");
        playerJson.put("password", "testpass");
        playerJson.put("role", "player");

        // Perform POST request to create a player
        MockHttpServletResponse createResponse = mockMvc.perform(post("/captains/createPlayer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(playerJson.toString()))
                .andReturn().getResponse();

        // Assert response status is 200 OK
        assertEquals(200, createResponse.getStatus());

        // Convert response content to Player object
        Player createdPlayer = objectMapper.readValue(createResponse.getContentAsString(), Player.class);

        // Assert player properties match what we sent
        assertEquals(123L, createdPlayer.getUserID());
        assertEquals("Test", createdPlayer.getFirstName());
        assertEquals("Player", createdPlayer.getLastName());
        assertEquals("test.player@example.com", createdPlayer.getEmail());

        // Verify player was added to captain's team
        Captain captain = captainRepository.findById(1L).orElse(null);
        assertNotNull(captain);
        assertNotNull(captain.getTeam(), "Captain should have a team");

        // Get players from the team
        Player savedPlayer = playerRepository.findById(123L).orElse(null);
        assertNotNull(savedPlayer, "Player should exist in repository");
        assertNotNull(savedPlayer.getTeam(), "Player should be assigned to a team");
        assertEquals(captain.getTeam().getTeamID(), savedPlayer.getTeam().getTeamID(),
                "Player should be on the captain's team");
    }

    @Test
    void addPlayerToTeam() throws Exception {
        // First, create a player with the proper Player entity structure
        Player player = new Player();
        player.setUserID(456L);
        player.setFirstName("Player");
        player.setLastName("To Add");
        player.setEmail("player.add@example.com");
        player.setPassword("addplayerpass");
        player.setRole("player");
        playerRepository.save(player);

        // Perform POST request to add the player to a captain's team
        MockHttpServletResponse response = mockMvc.perform(post("/captains/1/addPlayer/456")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert response status is 200 OK
        assertEquals(200, response.getStatus());

        // Verify player was added to the captain's team
        Captain captain = captainRepository.findById(1L).orElse(null);
        assertNotNull(captain);
        assertNotNull(captain.getTeam(), "Captain should have a team");

        // Retrieve the updated player
        Player updatedPlayer = playerRepository.findById(456L).orElse(null);
        assertNotNull(updatedPlayer, "Player should exist in repository");
        assertNotNull(updatedPlayer.getTeam(), "Player should be assigned to a team");
        assertEquals(captain.getTeam().getTeamID(), updatedPlayer.getTeam().getTeamID(),
                "Player should be on the captain's team");
    }

    @Test
    void removePlayerFromTeam() throws Exception {
        // First, create a player with proper Player entity structure and add to a team
        Player player = new Player();
        player.setUserID(789L);
        player.setFirstName("Player");
        player.setLastName("To Remove");
        player.setEmail("player.remove@example.com");
        player.setPassword("removeplayerpass");
        player.setRole("player");
        playerRepository.save(player);

        // Get the captain's team and add the player to it
        Captain captain = captainRepository.findById(1L).orElse(null);
        assertNotNull(captain);
        assertNotNull(captain.getTeam(), "Captain should have a team");

        // Add player to captain's team using the controller method
        mockMvc.perform(post("/captains/1/addPlayer/789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify player was added
        Player playerBeforeRemoval = playerRepository.findById(789L).orElse(null);
        assertNotNull(playerBeforeRemoval.getTeam(), "Player should be assigned to a team before removal");

        // Perform DELETE request to remove the player from teams
        MockHttpServletResponse response = mockMvc.perform(delete("/captains/removePlayer/789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert response status is 200 OK
        assertEquals(200, response.getStatus());
        assertEquals("Player removed successfully!!", response.getContentAsString());

        // Verify player was removed from the team
        Player playerAfterRemoval = playerRepository.findById(789L).orElse(null);
        assertNotNull(playerAfterRemoval, "Player should still exist in repository");
        assertNull(playerAfterRemoval.getTeam(), "Player should not be assigned to any team after removal");
    }

    @Test
    void deletePlayer() throws Exception {
        // First, create a player with proper Player entity structure
        Player player = new Player();
        player.setUserID(999L);
        player.setFirstName("Player");
        player.setLastName("To Delete");
        player.setEmail("player.delete@example.com");
        player.setPassword("deleteplayerpass");
        player.setRole("player");
        playerRepository.save(player);

        // Add the player to a captain's team
        Captain captain = captainRepository.findById(1L).orElse(null);
        assertNotNull(captain);
        assertNotNull(captain.getTeam(), "Captain should have a team");

        // Add player to captain's team using the controller method
        mockMvc.perform(post("/captains/1/addPlayer/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Verify player was added to a team
        Player playerBeforeDeletion = playerRepository.findById(999L).orElse(null);
        assertNotNull(playerBeforeDeletion.getTeam(), "Player should be assigned to a team before deletion");

        // Perform DELETE request to delete the player
        MockHttpServletResponse response = mockMvc.perform(delete("/captains/deletePlayer/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert response status is 200 OK
        assertEquals(200, response.getStatus());
        assertEquals("Player deleted successfully!", response.getContentAsString());

        // Verify player was deleted from the database
        Optional<Player> deletedPlayer = playerRepository.findById(999L);
        assertFalse(deletedPlayer.isPresent(), "Player should be deleted from repository");
    }

    @Test
    void removeNonExistentPlayer() throws Exception {
        // Perform DELETE request to remove a player that doesn't exist
        MockHttpServletResponse response = mockMvc.perform(delete("/captains/removePlayer/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert response status is 404 Not Found
        assertEquals(404, response.getStatus());
        assertEquals("Player not found.", response.getContentAsString());
    }

    @Test
    void deleteNonExistentPlayer() throws Exception {
        // Perform DELETE request to delete a player that doesn't exist
        MockHttpServletResponse response = mockMvc.perform(delete("/captains/deletePlayer/12345")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Assert response status is 404 Not Found
        assertEquals(404, response.getStatus());
        assertEquals("Player not found.", response.getContentAsString());
    }
}