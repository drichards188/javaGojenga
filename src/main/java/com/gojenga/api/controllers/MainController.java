package com.gojenga.api.controllers;

import com.gojenga.api.models.User;
import com.gojenga.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

//    @PostMapping(path="/add") // Map ONLY POST Requests
//    public @ResponseBody String addNewUser (@RequestBody Map<String, String> payload) {
//        // @ResponseBody means the returned String is the response, not a view name
//        // @RequestParam means it is a parameter from the GET or POST request
//
//        User n = new User();
//
//        String name = payload.get("name");
//        String email = payload.get("email");
//
//        n.setName(name);
//        n.setEmail(email);
//
//        try {
//            userRepository.save(n);
//            return "Saved";
//        } catch (Exception e) {
//            String errorMessage = e.getMessage();
//            return errorMessage;
//        }
//    }

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
    public ResponseEntity<Boolean> createUser(@RequestBody Map<String, String> payload) throws SQLException {
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

//    @PutMapping("")
//    public HashMap<String, String> updateUser(String name, @RequestBody Map<String, String> payload) throws SQLException {
//        String password = payload.get("password");
//        HashMap<String, String> response = new HashMap<>();
//
////        todo hash password
//
//        try {
//            if (name != null && password != null) {
//                String setStatement = String.format("balance = %s", password);
//                String whereStatement = String.format("name = '%s'", name);
//                HashMap<String, String> updateResult = updateData("ledgerTest", setStatement, whereStatement);
//                if (updateResult.containsKey("error")) {
//                    response.put("error", updateResult.get("error"));
//                    return response;
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            response.put("error", e.getMessage());
//            return response;
//        }
//
//        response.put("success", "user updated");
//        return response;
//    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteUser(@RequestParam(required = true) String name) throws SQLException {
        try {
            if (name != null) {
                String whereStatement = String.format("name = '%s'", name);
                Boolean deleteResult = userRepository.deleteByName(name);
                return ResponseEntity.ok(true);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}