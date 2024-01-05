package com.gojenga.api.repository;

import com.gojenga.api.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByUsername(String username);
//
//    @Transactional
//    @Modifying
//    @Query("update Account a set a.balance = ?2 where a.username = ?1")
//    Integer updateAccountByUsername(String username, Float balance);

    @Transactional
    Integer deleteAccountByUsername(String username);
}