package com.example.opituvalnik.repositories;

import com.example.opituvalnik.entities.Option;
import com.example.opituvalnik.entities.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OptionsRepo extends CrudRepository<Option, Long> {
    List<Option> findAll();

    List<Option> findByQuestion(Question question);
}
