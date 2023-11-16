package com.gojenga.api.repository;

import com.gojenga.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findEmailByUsername(String username);
    User findUserByUsername(String username);
    Optional<User> findByUsername(String username);
    User findUserByName(String name);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    boolean existsByUsername(String username);


    Integer deleteUserByName(String name);

}