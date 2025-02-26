package com.example.cms.model.repository;

import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findBySport(Sport sport);
}

