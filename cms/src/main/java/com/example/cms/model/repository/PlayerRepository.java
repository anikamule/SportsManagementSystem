package com.example.cms.model.repository;

import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Player;
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
public interface PlayerRepository extends JpaRepository<Player, Long> {

    // GET CAPTAIN INFORMATION - Fixed JPQL query
    @Query("SELECT new map(c.firstName as firstName, c.lastName as lastName, c.email as email) FROM Captain c JOIN c.team t WHERE t.teamID = :teamId")
    Optional<Map<String, Object>> findCaptainInformationByTeamId(@Param("teamId") String teamId);

    // RETRIEVE TEAM SCHEDULE - Fixed JPQL query
    @Query("SELECT g FROM Game g WHERE g.team1.teamID = :teamId OR g.team2.teamID = :teamId")
    List<Game> findTeamSchedule(@Param("teamId") String teamId);

    Player findByEmailAndPassword(String email, String password);

    @Query("SELECT COALESCE(MAX(p.userID), 0) FROM Player p")
    Long findMaxUserID();

    @Modifying
    @Transactional
    @Query("DELETE FROM Player p WHERE p.userID = :playerId")
    int deletePlayerById(@Param("playerId") Long playerId);
}