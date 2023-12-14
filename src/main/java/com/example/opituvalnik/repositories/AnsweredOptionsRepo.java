package com.example.opituvalnik.repositories;

import com.example.opituvalnik.entities.AnsweredOptions;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.entities.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnsweredOptionsRepo extends CrudRepository<AnsweredOptions, Long> {
    List<AnsweredOptions> findAll();

    List<AnsweredOptions> findByUser(Users user);
}
