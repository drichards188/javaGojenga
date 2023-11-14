package com.gojenga.api.controllers;

import com.gojenga.api.models.Account;
import com.gojenga.api.models.User;
import com.gojenga.api.repository.AccountRepository;
import com.gojenga.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/account") // This means URL's start with /demo (after Application path)
public class AccountController {
    @Autowired
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @PostMapping("")
    public ResponseEntity<Boolean> createAccount(@RequestBody Map<String, String> payload) throws SQLException {
        String name = payload.get("name");
        Float balance = Float.valueOf(payload.get("balance"));

//        todo hash password

        try {
            if (name != null && balance != null) {
                Account account = new Account(name, balance);

                accountRepository.save(account);
                return ResponseEntity.ok(true);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
