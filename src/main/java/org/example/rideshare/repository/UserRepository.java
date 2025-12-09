package org.example.rideshare.repository;

import java.util.Optional;

import org.example.rideshare.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);
}
