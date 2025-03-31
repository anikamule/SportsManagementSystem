package com.example.cms.model.repository;

import com.example.cms.model.entity.Captain;
import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Player;
import com.example.cms.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    // GET CAPTAIN INFORMATION
    @Query(value = "SELECT new map(c.firstName as firstName, c.lastName as lastName, c.email as email)" +
            " FROM Captain c WHERE c.team.teamID = :teamId", nativeQuery = true)                    // Note: Map allows us to return multiple strings
    Optional<Map<String, String>> findCaptainInformationByTeamId(@Param("teamId") String teamId);    // Note: optional prevents it from breaking


    // RETRIEVE TEAM SCHEDULE BY GETTING ALL GAMES
    // Note: since there are two teams in each game, we have to make sure we cover both team1 and team2
    @Query(value = "SELECT g FROM Game g WHERE g.team1.teamID = :teamId OR g.team2.teamID = :teamId", nativeQuery = true)
    List<Game> findTeamSchedule(@Param("teamId") String teamId);

    Player findByEmailAndPassword(String email, String password);

    @Query("SELECT COALESCE(MAX(CAST(p.userID AS integer)), 0) FROM Player p")
    Integer findMaxUserID();

    @Modifying
    @Transactional
    @Query("DELETE FROM Player p WHERE p.userID = :playerId")
    int deletePlayerById(@Param("playerId") String playerId);



}
