package com.example.cms.model.repository;

import com.example.cms.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT u FROM User u")
//    List<User> findAllUsers();

    // Create query for selecting by first name

    // Create query for selecting last name

    // Create query for selecting first and last name

    // Create Account (creates a user)

    // Login
}

