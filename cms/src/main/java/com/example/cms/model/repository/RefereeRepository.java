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
public interface RefereeRepository extends JpaRepository<Referee, String> {


    //@Query("SELECT r FROM Referee r")
    //List<Referee> findAllReferees();


    // Update game status to "Finished" (only if referee is assigned to the game)
    //@Modifying
    //@Transactional
    //@Query(value = "UPDATE Game g SET g.status = 'Finished' WHERE g.id = :gameId" +
    //" AND EXISTS (SELECT 1 FROM Referee r WHERE r.userID = :refereeUserId AND :gameId MEMBER OF r.gameIDs)", nativeQuery = true)
    //int updateGameStatus(@Param("refereeUserId") Long refereeUserId, @Param("gameId") Long gameId);


    // get list of games
    @Modifying
    @Transactional
    @Query("SELECT g FROM Game g")
    List<Game> getAllGames();

    // get list of games with specific referee assigned
//    @Modifying
//    @Transactional
//    @Query("SELECT g FROM Game g WHERE g.referee.userID = :userID")
//    List<Game> getGamesAssigned(@Param("userID") Long userID);
//
//
//    // assign specific referee to game
//    @Modifying
//    @Transactional
//    @Query("UPDATE Game g SET g.referee = (SELECT r FROM Referee r WHERE r.userID = :userID) WHERE g.gameID = :gameID")
//    int assignRefereeToGame(@Param("gameID") Long gameID, @Param("userID") Long userID);
//
//
//    // update game status to finished (when referee is assigned to game)
//    @Modifying
//    @Transactional
//    @Query("UPDATE Game g SET g.status = 'Finished' WHERE g.gameID = :gameID AND g.referee IS NOT NULL")
//    int setGameStatusToFinished(@Param("gameID") Long gameID);
//
//
//    // specific referee assigned to game updates game score
////    @Modifying
////    @Transactional
////    @Query("UPDATE Game g SET g.score = :score WHERE g.gameID = :gameID AND g.referee = (SELECT r FROM Referee r WHERE r.userID = :userID)")
////    int updateGameScore(@Param("gameID") Long gameID, @Param("userID") Long userID, @Param("score") int[] score);
//
//
//    // game status is either upcoming (game not played) or completed (game played) -> (updates when referee updates score)
//    @Query("UPDATE Game g SET g.score = :score, g.status = " +
//            "CASE WHEN :score IS NOT NULL THEN 'Completed' ELSE g.status END " +
//            "WHERE g.gameID = :gameID AND g.referee = (SELECT r FROM Referee r WHERE r.userID = :userID)")
//    int updateGameScoreAndStatus(@Param("gameID") Long gameID, @Param("userID") Long userID, @Param("score") int[] score);

    Referee findByEmailAndPassword(String email, String password);

}

