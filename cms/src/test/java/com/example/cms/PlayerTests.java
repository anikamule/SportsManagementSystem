package com.example.cms;

import com.example.cms.model.entity.Player;
import com.example.cms.model.repository.PlayerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class PlayerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void retrieveAllPlayers() throws Exception {
        mockMvc.perform(get("/players")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3)); // 3 players in the test data
    }

    @Test
    void retrievePlayerById() throws Exception {
        // Players from SQL script have IDs 8, 9, 10
        Long playerId = 8L;

        mockMvc.perform(get("/players/{playerId}", playerId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userID").value(playerId))
                .andExpect(jsonPath("$.firstName").value("Player"))
                .andExpect(jsonPath("$.lastName").value("One"));
    }

    @Test
    void retrieveNonExistentPlayer() throws Exception {
        // Test behavior when trying to retrieve a player that doesn't exist
        Long nonExistentId = 999999L;

        mockMvc.perform(get("/players/{playerId}", nonExistentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveCaptainInfo() throws Exception {
        // From the SQL script, team T001 has captain with userID 1
        String teamId = "T001";

        mockMvc.perform(get("/players/getCaptainInfo/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.teamID").value(teamId));
    }

    @Test
    void retrieveTeamSchedule() throws Exception {
        // From the SQL script, team T001 has multiple games
        String teamId = "T001";

        MvcResult result = mockMvc.perform(get("/players/getSchedule/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(4)) // 4 games for T001
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        List<Map<String, Object>> games = objectMapper.readValue(responseBody, List.class);

        // Verify that we can find this team in the SQL script
        assertTrue(games.stream().anyMatch(game ->
                game.containsValue(1) || // gameID from SQL script
                        game.containsValue(3) ||
                        game.containsValue(5) ||
                        game.containsValue(7)
        ), "Games for team T001 should be present");
    }
}