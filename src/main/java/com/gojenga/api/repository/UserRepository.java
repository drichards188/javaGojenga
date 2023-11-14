package com.gojenga.api.repository;


import com.gojenga.api.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
    @Transactional
    User findUserByName(String name);
    @Transactional
    Integer deleteByName(String name);
}