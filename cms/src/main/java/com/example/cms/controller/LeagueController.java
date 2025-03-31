package com.example.cms.controller;


import com.example.cms.model.entity.Game;
import com.example.cms.model.entity.League;
import com.example.cms.model.entity.Team;
import com.example.cms.model.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/leagues")
public class LeagueController {


    private final LeagueRepository leagueRepository;


    @Autowired
    public LeagueController(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    // Get all leagues
    @GetMapping()
    public List<League> retrieveAllLeagues() {
        return leagueRepository.findAll();
    }


//    // Get league by ID
//    @GetMapping("/{leagueID}")
//    public League getLeagueById(@PathVariable Long leagueID) {
//        return leagueRepository.findLeagueById(leagueID);
//    }
//
//
//    // Get all teams in a league
//    @GetMapping("/{leagueID}/teams")
//    public List<Team> getTeamsByLeagueId(@PathVariable Long leagueID) {
//        return leagueRepository.findTeamsByLeagueId(leagueID);
//    }
//
//
//    // Get all games in a league
//    @GetMapping("/{leagueID}/games")
//    public List<Game> getGamesByLeagueId(@PathVariable Long leagueID) {
//        return leagueRepository.findGamesByLeagueId(leagueID);
//    }
//
//
//    // Get standings of a league (all teams ordered by ranking)
//    @GetMapping("/{leagueID}/standings")
//    public List<Team> getStandings(@PathVariable Long leagueID) {
//        return leagueRepository.getStandingsByLeagueId(leagueID);
//    }
//
//
//    // Get the schedule of a league
//    @GetMapping("/{leagueID}/schedule")
//    public List<Game> getSchedule(@PathVariable Long leagueID) {
//        return leagueRepository.findGamesByLeagueId(leagueID);
//    }
}

