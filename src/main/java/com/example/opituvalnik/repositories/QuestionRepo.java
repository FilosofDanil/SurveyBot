package com.example.opituvalnik.repositories;

import com.example.opituvalnik.entities.Question;
import com.example.opituvalnik.entities.Quiz;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepo extends CrudRepository<Question, Long> {
    List<Question> findAll();

    List<Question> findByQuiz(Quiz quiz);

    @Query(value = "SELECT * from questions where quiz_id = :quiz and question_num = :num",
            nativeQuery = true)
    List<Question> findByQuizAndQuestionNum(@Param("quiz") Long quiz, @Param("num") Integer num);
}
