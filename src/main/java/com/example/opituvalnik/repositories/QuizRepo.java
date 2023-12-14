package com.example.opituvalnik.repositories;

import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.entities.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuizRepo extends CrudRepository<Quiz, Long> {
    List<Quiz> findAll();

    List<Quiz> findByUser(Users user);
}
