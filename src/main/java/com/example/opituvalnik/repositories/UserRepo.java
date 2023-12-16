package com.example.opituvalnik.repositories;

import com.example.opituvalnik.entities.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public interface UserRepo extends CrudRepository<Users, Long> {
    List<Users> findAll();

    Users findByUsername(String username);

    @Query(value = "select u.chat_id, u.id, u.tg_name,u. username from users u\n" +
            "    left join answered_options ao on u.id = ao.user_id\n" +
            "    left join options  o on o.id = ao.option_id\n" +
            "    left join questions q on q.id = o.question_id\n" +
            "    left join quizes q2 on q2.id = q.quiz_id\n" +
            "    where quiz_id = :quizId",nativeQuery = true)
    List<Users> usersPassedQuiz(@Param("quizId") Long quizId);
}
