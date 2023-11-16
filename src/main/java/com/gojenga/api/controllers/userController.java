package com.gojenga.api.controllers;

import com.gojenga.api.models.MyUser;
import com.gojenga.api.repository.MyUserRepository;
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
    @Qualifier("myUserRepository")
    private MyUserRepository myUserRepository;

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<MyUser> getAllUsers() {
        // This returns a JSON or XML with the users
        return myUserRepository.findAll();
    }

    @GetMapping(path = "")
    public ResponseEntity<MyUser> getUser(@RequestParam String name) {
        MyUser myUser = myUserRepository.findUserByName(name);

        if (!myUser.isEmpty()) {
            return ResponseEntity.ok(myUser);
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
                MyUser myUser = new MyUser();
                myUser.setName(name);
                myUser.setPassword(password);

                myUserRepository.save(myUser);
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
            MyUser myUser = myUserRepository.findUserByName(name);
            myUser.setPassword(password);

            if (name != null && password != null) {
                myUserRepository.save(myUser);
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
                Integer deleteResult = myUserRepository.deleteByName(name);
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