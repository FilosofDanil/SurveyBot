package com.example.opituvalnik.services.impl;

import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.repositories.QuizRepo;
import com.example.opituvalnik.services.QuizService;
import com.example.opituvalnik.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepo quizRepo;

    private final StatisticService statisticService;


    @Override
    public Quiz findPopular() {
        List<Quiz> surveys = quizRepo.findAll();
        int biggestCount = 0;
        int biggestId = 0, k = 0;
        for(Quiz survey: surveys){
            if(statisticService.passedTimes(survey) >= biggestCount){
                biggestId = k;
            }
            k++;
        }
        if(surveys.isEmpty()){
            return null;
        }
        return surveys.get(biggestId);
    }
}
