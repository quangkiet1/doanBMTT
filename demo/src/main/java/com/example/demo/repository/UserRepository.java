package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.model.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.demo.model.user;

public interface UserRepository extends MongoRepository<user, String> {
    user findByUsername(String username);
}
