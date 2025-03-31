package com.example.cms.model.repository;

import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    // Get all games
    @Query("SELECT g FROM Game g")
    List<Game> findAllGames();

    // Get a specific game by ID
    @Query("SELECT g FROM Game g WHERE g.gameID = :gameID")
    Game findGameById(Long gameID);

    // Get all games in a league
    @Query("SELECT g FROM Game g WHERE g.league.leagueID = :leagueID")
    List<Game> findGamesByLeagueId(Long leagueID);

    // Get all games of a specific team
    @Query("SELECT g FROM Game g WHERE g.team1.teamID = :teamID OR g.team2.teamID = :teamID")
    List<Game> findGamesByTeamId(Long teamID);

    // Get game details (date, location of game)
    @Query("SELECT g.datetime, g.location FROM Game g WHERE g.gameID = :gameID")
    Object[] findGameDetailsById(Long gameID);
}
