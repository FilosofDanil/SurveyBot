package com.example.opituvalnik.services;

import com.example.opituvalnik.entities.Question;
import com.example.opituvalnik.entities.Quiz;

public interface StatisticService {
    String getSurveyStats(Quiz quiz);

    Integer passedTimes(Quiz quiz);
}
