package com.example.cms.model.repository;


import com.example.cms.model.entity.League;
import com.example.cms.model.entity.Team;
import com.example.cms.model.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

//    // Get a specific league by ID
//    @Query("SELECT l FROM League l WHERE l.leagueID = :leagueID")
//    League findLeagueById(Long leagueID);
//
//    // Get all teams in a league
//    @Query("SELECT t FROM Team t WHERE t.league.leagueID = :leagueID")
//    List<Team> findTeamsByLeagueId(Long leagueID);
//
//    // Get all games in a league
//    @Query("SELECT g FROM Game g WHERE g.league.leagueID = :leagueID")
//    List<Game> findGamesByLeagueId(Long leagueID);
//
//    // Get schedule for a league (all games in a league)
//    @Query("SELECT g FROM Game g WHERE g.league.leagueID = :leagueID ORDER BY g.date, g.time")
//    List<Game> getScheduleByLeagueId(Long leagueID);
//
//    // Get standings for a league (teams ordered by ranking or any metric)
//    @Query("SELECT t FROM Team t WHERE t.league.leagueID = :leagueID ORDER BY t.ranking")
//    List<Team> getStandingsByLeagueId(Long leagueID);
}
