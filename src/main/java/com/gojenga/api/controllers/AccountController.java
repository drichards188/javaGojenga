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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = "api/account") // This means URL's start with /demo (after Application path)
public class AccountController {
    @Autowired
    @Qualifier("accountRepository")
    private AccountRepository accountRepository;

    @GetMapping("")
    public ResponseEntity<Account> getAccount(@RequestParam String username) {
        try {
            Account account = accountRepository.findAccountByUsername(username);

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
        String username = payload.get("username");
        Float balance = Float.valueOf(payload.get("balance"));

        try {
            if (username != null && balance != null) {
                Account account = new Account();
                account.setUsername(username);
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
        String username = payload.get("username");
        Float balance = Float.valueOf(payload.get("balance"));

        try {
            Account account = accountRepository.findAccountByUsername(username);
            account.setBalance(balance);

            if (username != null && balance != null) {
                accountRepository.save(account);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/deposit")
    public ResponseEntity<Boolean> deposit(@RequestBody Map<String, String> payload) throws SQLException {
        String username = payload.get("username");
        Float amount = Float.valueOf(payload.get("amount"));

        try {
            Account account = accountRepository.findAccountByUsername(username);
            Float newBalance = amount + account.getBalance();
            account.setBalance(newBalance);

            if (username != null && amount != null) {
                accountRepository.save(account);
                return new ResponseEntity<>(true, HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/transaction")
    public ResponseEntity<Boolean> transaction(@RequestBody Map<String, String> payload) throws SQLException {
        String sender = payload.get("sender");
        String receiver = payload.get("receiver");
        Float amount = Float.valueOf(payload.get("amount"));

        try {
            Account senderAccount = accountRepository.findAccountByUsername(sender);
            Float senderBalance = senderAccount.getBalance();
            if (senderBalance < amount) {
                throw new Exception("Insufficient funds");
            }
            Account receiverAccount = accountRepository.findAccountByUsername(receiver);
            Float receiverBalance = receiverAccount.getBalance();

            senderAccount.setBalance(senderBalance - amount);
            receiverAccount.setBalance(receiverBalance + amount);

            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);
            return new ResponseEntity<>(true, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<Boolean> deleteAccount(@RequestParam String username) {
        try {
            if (username != null) {
                Integer deleteResponse = accountRepository.deleteAccountByUsername(username);
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
