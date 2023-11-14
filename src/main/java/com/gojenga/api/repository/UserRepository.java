package com.gojenga.api.repository;


import com.gojenga.api.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserByName(String name);
    Boolean deleteByName(String name);
}