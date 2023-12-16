package com.example.opituvalnik.services.impl;

import com.example.opituvalnik.entities.*;
import com.example.opituvalnik.repositories.AnsweredOptionsRepo;
import com.example.opituvalnik.repositories.OptionsRepo;
import com.example.opituvalnik.repositories.QuestionRepo;
import com.example.opituvalnik.repositories.UserRepo;
import com.example.opituvalnik.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final QuestionRepo questionRepo;

    private final OptionsRepo optionsRepo;

    private final UserRepo userRepo;

    private final AnsweredOptionsRepo answeredOptionsRepo;

    @Override
    public String getSurveyStats(Quiz quiz) {
        if (!quiz.getPublished()){
            return "You haven't finished your survey yet!";
        }
        List<Question> questions = questionRepo.findByQuiz(quiz);
        StringBuilder stringBuilder = new StringBuilder("Statistics: \n");

        for (Question question : questions) {
            stringBuilder.append(question.getQuestionName());
            List<AnsweredOptions> allAnswers = answeredOptionsRepo.findAllByQuestion(question.getId());
            long biggestOptionId = 0;
            double theBiggestPercentage = 0;
            stringBuilder.append(" | popular answer: ");
            List<Option> options = optionsRepo.findByQuestion(question);
            for (Option option : options) {
                List<AnsweredOptions> answeredOptions = answeredOptionsRepo.findByOption(option);
                double percentage = (double) answeredOptions.size() / allAnswers.size() * 100;
                if (percentage >= theBiggestPercentage) {
                    theBiggestPercentage = percentage;
                    biggestOptionId = option.getId();
                }
            }
            if (biggestOptionId == 0) {
                return "No one has passed your survey";
            }
            Option preferredOption = optionsRepo.findById(biggestOptionId).get();
            stringBuilder.append(preferredOption.getOptionText() + " " + theBiggestPercentage);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public Integer passedTimes(Quiz quiz) {
        List<Users> usersPassed = userRepo.usersPassedQuiz(quiz.getId());
        return usersPassed.size() / quiz.getQuestionsCount();
    }
}
