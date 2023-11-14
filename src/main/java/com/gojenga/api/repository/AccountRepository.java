package com.gojenga.api.repository;

import com.gojenga.api.models.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByName(String name);

    @Transactional
    @Modifying
    @Query("update Account a set a.balance = ?2 where a.name = ?1")
    Integer updateAccountByName(String name, Float balance);

    @Transactional
    Integer deleteAccountByName(String name);
}