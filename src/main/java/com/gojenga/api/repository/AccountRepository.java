package com.gojenga.api.repository;

import com.gojenga.api.models.Account;
import com.gojenga.api.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("accountRepository")
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Account findAccountByName(String name);
}