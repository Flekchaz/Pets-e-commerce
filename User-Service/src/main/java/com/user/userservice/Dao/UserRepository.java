package com.user.userservice.Dao;

import com.user.userservice.Entities.User;
import com.user.userservice.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List <User> findByRole(Role role);
    List<User> findByNameContainingIgnoreCase(String name);
}
