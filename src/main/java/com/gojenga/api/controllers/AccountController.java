package com.gojenga.api.controllers;

import com.gojenga.api.models.Account;
import com.gojenga.api.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/account") // This means URL's start with /demo (after Application path)
public class AccountController {
    @Autowired
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @GetMapping("")
    public ResponseEntity<Account> getAccount(@RequestParam String name) {
        try {
            Account account = accountRepository.findAccountByName(name);

            if (account != null) {
                return new ResponseEntity<>(account, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("")
    public ResponseEntity<Boolean> createAccount(@RequestBody Map<String, String> payload) throws SQLException {
        String name = payload.get("name");
        Float balance = Float.valueOf(payload.get("balance"));

//        todo hash password
        try {
            if (name != null && balance != null) {
                Account account = new Account();
                account.setName(name);
                account.setBalance(balance);

                accountRepository.save(account);
                return new ResponseEntity<>(true, HttpStatus.OK);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @PutMapping("")
    public ResponseEntity<Boolean> updateAccount(@RequestBody Map<String, String> payload) throws SQLException {
        String name = payload.get("name");
        Float balance = Float.valueOf(payload.get("balance"));

        try {
            Account account = accountRepository.findAccountByName(name);
            account.setBalance(balance);

            if (name != null && balance != null) {
                accountRepository.save(account);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteAccount(@RequestParam String name) {
        try {
            if (name != null) {
                Integer deleteResponse = accountRepository.deleteAccountByName(name);
                if (deleteResponse > 0) {
                    return new ResponseEntity<>(true, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
                }

            } else {
                return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
