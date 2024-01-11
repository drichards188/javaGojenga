package com.gojenga.api.controllers;

import com.gojenga.api.models.User;
import com.gojenga.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/user")
public class UserController {
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Qualifier("userRepository")
    private final UserRepository userRepository;

    @GetMapping(path = "")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        try {
           User user = userRepository.findUserByUsername(username);

            if (user != null && !user.isEmpty()) {
                User respUser = new User();
                return ResponseEntity.ok(respUser);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PutMapping("")
    public ResponseEntity<Boolean> updateAccount(@RequestBody Map<String, String> payload) throws Exception {
        String username = payload.get("username");
        String password = payload.get("password");

        try {
            User user = userRepository.findUserByUsername(username);
            user.setPassword(password);

            if (username != null && password != null) {
                userRepository.save(user);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteUser(@RequestParam(required = true) String name) throws SQLException {
        try {
            if (name != null) {
                Integer deleteResult = userRepository.deleteUserByName(name);
                if (deleteResult > 0) {
                    return ResponseEntity.ok(true);
                } else {
                    return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);

                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }

    }
}