package com.example.opituvalnik.repositories;

import com.example.opituvalnik.entities.AnsweredOptions;
import com.example.opituvalnik.entities.Option;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.entities.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnsweredOptionsRepo extends CrudRepository<AnsweredOptions, Long> {
    List<AnsweredOptions> findAll();

    List<AnsweredOptions> findByUser(Users user);

    List<AnsweredOptions> findByOption(Option option);

    @Query(value = "select answered_options.id, answered_options.option_id, answered_options.user_id from answered_options join options o on o.id = answered_options.option_id join questions q on q.id = o.question_id join quizes q2 on q2.id = q.quiz_id where q.quiz_id = :quizId",
            nativeQuery = true)
    List<AnsweredOptions> findAllByQuiz(@Param("quizId") Long quizId);

    @Query(value = "select answered_options.id, answered_options.option_id, answered_options.user_id from answered_options join options o on o.id = answered_options.option_id join questions q on q.id = o.question_id where q.id = :questionId",
            nativeQuery = true)
    List<AnsweredOptions> findAllByQuestion(@Param("questionId") Long questionId);
}
