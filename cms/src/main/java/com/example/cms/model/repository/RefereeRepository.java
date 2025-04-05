package com.example.cms.model.repository;

import com.example.cms.model.entity.Captain;
import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Referee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {

    @Query("SELECT r FROM Referee r")
    List<Referee> findAllReferees();

    // Update game status to "Completed" (only if referee is assigned to the game)
    @Modifying
    @Transactional
    @Query("UPDATE Game g SET g.gameStatus = 'Completed' WHERE g.gameID = :gameId " +
            "AND g.referee.userID = :refereeUserId")
    int updateGameStatus(@Param("refereeUserId") Long refereeUserId, @Param("gameId") Long gameId);

    // get list of games
    @Query("SELECT g FROM Game g")
    List<Game> getAllGames();

    // get list of games with specific referee assigned
    @Query("SELECT g FROM Game g WHERE g.referee.userID = :userID")
    List<Game> getGamesAssigned(@Param("userID") Long userID);

    // assign specific referee to game
    @Modifying
    @Transactional
    @Query("UPDATE Game g SET g.referee = (SELECT r FROM Referee r WHERE r.userID = :userID) WHERE g.gameID = :gameID")
    int assignRefereeToGame(@Param("gameID") Long gameID, @Param("userID") Long userID);

    // update game status to completed
    @Modifying
    @Transactional
    @Query("UPDATE Game g SET g.gameStatus = 'Completed' WHERE g.gameID = :gameID AND g.referee IS NOT NULL")
    int setGameStatusToCompleted(@Param("gameID") Long gameID);

    // specific referee assigned to game updates game score
    @Modifying
    @Transactional
    @Query("UPDATE Game g SET g.teamScore1 = :teamScore1, g.teamScore2 = :teamScore2 " +
            "WHERE g.gameID = :gameID AND g.referee.userID = :userID")
    int updateGameScore(@Param("gameID") Long gameID, @Param("userID") Long userID,
                        @Param("teamScore1") int teamScore1, @Param("teamScore2") int teamScore2);

    // game status is either upcoming (game not played) or completed (game played) -> (updates when referee updates score)
    @Modifying
    @Transactional
    @Query("UPDATE Game g SET g.teamScore1 = :teamScore1, g.teamScore2 = :teamScore2, " +
            "g.gameStatus = CASE WHEN :teamScore1 IS NOT NULL AND :teamScore2 IS NOT NULL THEN 'Completed' ELSE g.gameStatus END " +
            "WHERE g.gameID = :gameID AND g.referee.userID = :userID")
    int updateGameScoreAndStatus(@Param("gameID") Long gameID, @Param("userID") Long userID,
                                 @Param("teamScore1") Integer teamScore1, @Param("teamScore2") Integer teamScore2);

    Referee findByEmailAndPassword(String email, String password);
}