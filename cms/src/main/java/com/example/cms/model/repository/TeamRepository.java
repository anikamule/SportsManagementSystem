package com.example.cms.model.repository;


import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.Player;
import com.example.cms.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // Get a specific team by ID
    @Query("SELECT t FROM Team t WHERE t.teamID = :teamID")
    Team findTeamById(String teamID);

    // Get all players in a team
    @Query("SELECT p FROM Player p WHERE p.team.teamID = :teamID")
    List<Player> findPlayersByTeamId(String teamID);

    // Get the captain of a specific team
    @Query("SELECT t.captain FROM Team t WHERE t.teamID = :teamID")
    Player findCaptainByTeamId(String teamID);

    // Get the schedule (games) for a specific team
    @Query("SELECT g FROM Game g WHERE g.team1.teamID = :teamID OR g.team2.teamID = :teamID ORDER BY g.datetime")
    List<Game> findScheduleByTeamId(String teamID);

    // Get the team standings based on ranking
    @Query("SELECT t FROM Team t WHERE t.league.leagueID = :leagueID ORDER BY t.ranking")
    List<Team> getStandingsByLeagueId(String leagueID);
}

