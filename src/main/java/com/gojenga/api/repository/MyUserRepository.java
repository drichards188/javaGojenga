package com.gojenga.api.repository;


import com.gojenga.api.models.MyUser;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository("myUserRepository")
public interface MyUserRepository extends CrudRepository<MyUser, Integer> {
    @Transactional
    MyUser findUserByName(String name);
    @Transactional
    Integer deleteByName(String name);
}