package com.gojenga.api.controllers;

import com.gojenga.api.models.User;
import com.gojenga.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/user") // This means URL's start with /demo (after Application path)
public class userController {
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path = "")
    public ResponseEntity<User> getUser(@RequestParam String name) {
        User user = userRepository.findUserByName(name);

        if (!user.isEmpty()) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    @PostMapping("")
    public ResponseEntity<Boolean> createUser(@RequestBody Map<String, String> payload) {
        String name = payload.get("name");
        String password = payload.get("password");

//        todo hash password

        try {
            if (name != null && password != null) {
                User user = new User();
                user.setName(name);
                user.setPassword(password);

                userRepository.save(user);
                return ResponseEntity.ok(true);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PutMapping("")
    public ResponseEntity<Boolean> updateAccount(@RequestBody Map<String, String> payload) throws Exception {
        String name = payload.get("name");
        String password = payload.get("password");

        try {
            User user = userRepository.findUserByName(name);
            user.setPassword(password);

            if (name != null && password != null) {
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
                String whereStatement = String.format("name = '%s'", name);
                Integer deleteResult = userRepository.deleteByName(name);
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