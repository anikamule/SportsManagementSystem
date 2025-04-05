package com.example.cms.model.repository;

import com.example.cms.model.entity.Admin;
import com.example.cms.model.entity.League;
import com.example.cms.model.entity.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Create a new league
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO leagues (leagueID, leagueName, leagueGender, divisionNum, leagueSport) " +
            "VALUES (:leagueID, :leagueName, :leagueGender, :divisionNum, :leagueSport)", nativeQuery = true)
    void createLeague(
            @Param("leagueID") String leagueID,
            @Param("leagueName") String leagueName,
            @Param("leagueGender") String leagueGender,
            @Param("divisionNum") String divisionNum,
            @Param("leagueSport") String leagueSport);

    // Create a new team
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO teams (teamID, teamName, captainID, leagueID) " +
            "VALUES (:teamID, :teamName, :captainID, :leagueID)", nativeQuery = true)
    void createTeam(
            @Param("teamID") String teamID,
            @Param("teamName") String teamName,
            @Param("captainID") String captainID,
            @Param("leagueID") String leagueID);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO captains (userID, firstName, lastName, email, role) " +
            "VALUES (:userID, :firstName, :lastName, :email, :role)", nativeQuery = true)
    void createCaptain(
            @Param("userID") String userID,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("role") String role);

    // Assign a captain to a team
    @Modifying
    @Transactional
    @Query(value = "UPDATE teams SET captainID = :captainID WHERE teamID = :teamID " +
            "AND EXISTS (SELECT 1 FROM admins WHERE userID = :adminID AND role = 'admin')",
            nativeQuery = true)
    void assignCaptainByAdmin(
            @Param("teamID") String teamID,
            @Param("captainID") String captainID,  // Changed to String to match User entity
            @Param("adminID") String adminID);     // Changed to String to match User entity

    // Create a new game
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO games (gameID, datetime, location, team1ID, team2ID, teamScore1, teamScore2, gameStatus, refereeID, leagueID) " +
            "VALUES (:gameID, :datetime, :location, :team1ID, :team2ID, :teamScore1, :teamScore2, :gameStatus, :refereeID, :leagueID)", nativeQuery = true)
    void createGame(
            @Param("gameID") Long gameID,
            @Param("datetime") String datetime,
            @Param("location") String location,
            @Param("team1ID") String team1ID,
            @Param("team2ID") String team2ID,
            @Param("teamScore1") int teamScore1,
            @Param("teamScore2") int teamScore2,
            @Param("gameStatus") String gameStatus,
            @Param("refereeID") Long refereeID,
            @Param("leagueID") String leagueID);

    // Assign a referee to a game
    @Modifying
    @Transactional
    @Query(value = "UPDATE games SET refereeID = :refereeID WHERE gameID = :gameID", nativeQuery = true)
    void assignReferee(@Param("gameID") Long gameID, @Param("refereeID") Long refereeID);

    // Update the game score
    @Modifying
    @Transactional
    @Query(value = "UPDATE games SET teamScore1 = :teamScore1, teamScore2 = :teamScore2 WHERE gameID = :gameID", nativeQuery = true)
    void updateGameScore(
            @Param("gameID") Long gameID,
            @Param("teamScore1") int teamScore1,
            @Param("teamScore2") int teamScore2);

    // Update game status
    @Modifying
    @Transactional
    @Query(value = "UPDATE games SET gameStatus = :gameStatus WHERE gameID = :gameID", nativeQuery = true)
    void updateGameStatus(@Param("gameID") Long gameID, @Param("gameStatus") String gameStatus);

    // Delete a league
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM leagues WHERE leagueID = :leagueID", nativeQuery = true)
    void deleteLeague(@Param("leagueID") String leagueID);

    // Delete a team
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM teams WHERE teamID = :teamID", nativeQuery = true)
    void deleteTeam(@Param("teamID") String teamID);

    // Delete a game
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM games WHERE gameID = :gameID", nativeQuery = true)
    void deleteGame(@Param("gameID") Long gameID);

    // Remove a captain from a team
    @Modifying
    @Transactional
    @Query(value = "UPDATE teams SET captainID = NULL WHERE teamID = :teamID " +
            "AND EXISTS (SELECT 1 FROM admins WHERE userID = :adminID AND role = 'admin')",
            nativeQuery = true)
    void removeCaptainByAdmin(
            @Param("teamID") String teamID,// Changed to String to match User entity
            @Param("adminID") String adminID);     // Changed to String to match User entity

    // NEW DELETE FOR LEAGUES
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM games WHERE team1ID IN (SELECT teamID FROM teams WHERE leagueID = :leagueID) OR team2ID IN (SELECT teamID FROM teams WHERE leagueID = :leagueID)", nativeQuery = true)
    void deleteGamesByTeamsInLeague(@Param("leagueID") String leagueID);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM games WHERE leagueID = :leagueID", nativeQuery = true)
    void deleteGamesByLeagueId(@Param("leagueID") String leagueID);


    @Modifying
    @Transactional
    @Query(value = "DELETE FROM teams WHERE leagueID = :leagueID", nativeQuery = true)
    void deleteTeamsByLeagueId(@Param("leagueID") String leagueId);

    Admin findByEmailAndPassword(String email, String password);



    // NEW Delete Captain from All Teams
    @Modifying
    @Transactional
    @Query(value = "UPDATE teams SET captainID = NULL WHERE captainID = :captainID", nativeQuery = true)
    void removeCaptainFromAllTeams(@Param("captainID") Long captainID);

    // Create Referee
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO referees (userID, firstName, lastName, email, role, password) " +
            "VALUES (:userID, :firstName, :lastName, :email, :role, :password)", nativeQuery = true)
    void createReferee(
            @Param("userID") String userID,
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("email") String email,
            @Param("role") String role,
            @Param("password") String password);



}