package com.example.cms.controller;

import com.example.cms.controller.dto.LoginResponseDTO;
import com.example.cms.controller.exceptions.UserNotFoundException;
import com.example.cms.model.entity.Captain;
import com.example.cms.model.entity.Player;
import com.example.cms.model.entity.Referee;
import com.example.cms.model.entity.User;
import com.example.cms.model.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private final UserRepository repository;
    private final RefereeRepository refereeRepository;
    private final CaptainRepository captainRepository;
    private final PlayerRepository playerRepository;
    private final AdminRepository adminRepository;

    public UserController(UserRepository repository, RefereeRepository refereeRepository, CaptainRepository captainRepository, PlayerRepository playerRepository, AdminRepository adminRepository) {
        this.repository = repository;
        this.refereeRepository = refereeRepository;
        this.captainRepository = captainRepository;
        this.playerRepository = playerRepository;
        this.adminRepository = adminRepository;
    }

    // FIND ALL USERS
    @GetMapping("/users")
    List<User> retrieveAllUsers() {
        return repository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password) {

        User user = null;
        String role = null;

        if ((user = captainRepository.findByEmailAndPassword(email, password)) != null) {
            role = "captain";
        } else if ((user = playerRepository.findByEmailAndPassword(email, password)) != null) {
            role = "player";
        } else if ((user = adminRepository.findByEmailAndPassword(email, password)) != null) {
            role = "admin";
        } else if ((user = refereeRepository.findByEmailAndPassword(email, password)) != null) {
            role = "referee";
        }
        else {
            role = "issue";
        }

        if (user != null) {
            System.out.println("User role: " + role);  // Log the role to verify it's correct
            // Return the user details along with the role
            return ResponseEntity.ok(new LoginResponseDTO(user.getUserID(), user.getFirstName(), user.getLastName(), user.getEmail(), role));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    // registration
    @PostMapping("/users/create")
    public ResponseEntity<String> createUser(
//            @RequestParam String userID,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password) {
        try {
            String role = "player"; // default is player

            // Generate the next available user ID
            Integer maxUserID = playerRepository.findMaxUserID();
            Integer nextUserID = (maxUserID != null) ? maxUserID + 1 : 1;
            String userID = nextUserID.toString(); // Store as a numeric string


            Player player = new Player();
            player.setUserID(userID);
            player.setFirstName(firstName);
            player.setLastName(lastName);
            player.setEmail(email);
            player.setPassword(password);
            player.setRole(role);
            playerRepository.save(player);

            return ResponseEntity.ok("User created successfully as " + role);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error creating user: " + e.getMessage());
        }
    }

}

