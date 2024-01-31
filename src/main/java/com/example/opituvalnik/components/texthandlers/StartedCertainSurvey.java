package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.components.keyboardsender.ReplyKeyboardSender;
import com.example.opituvalnik.entities.*;
import com.example.opituvalnik.repositories.AnsweredOptionsRepo;
import com.example.opituvalnik.repositories.OptionsRepo;
import com.example.opituvalnik.repositories.QuestionRepo;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.opituvalnik.services.UserService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Log4j2
public class StartedCertainSurvey implements TextHandler {
    private final Map<Long, List<AnsweredOptions>> answers = new HashMap<>();

    private final Map<Long, Quiz> currentSurveys = new HashMap<>();

    private final Map<Long, Integer> counter = new HashMap<>();

    private final EmptyTextHandler emptyTextHandler;

    private final QuestionRepo questionRepo;

    private final OptionsRepo optionsRepo;

    private final AnsweredOptionsRepo answeredOptionsRepo;

    private final UserService userService;

    private final TelegramBotService telegramBotService;

    @Override
    public TextHandler handle(UserRequest request) {
        try {
            Quiz survey = currentSurveys.get(request.getChatId());
            List<Question> questions = questionRepo.findByQuiz(survey);
            List<AnsweredOptions> answeredOptions = answers.get(request.getChatId());
            List<AnsweredOptions> userOptions = answeredOptionsRepo.findByUser(userService.getByUsername(request.getUpdate().getMessage().getChat().getUserName()));
            userOptions = userOptions.stream().filter(answeredOption -> answeredOption.getOption().getQuestion().getQuiz().getId().equals(survey.getId())).toList();
            if (!userOptions.isEmpty()) {
                telegramBotService.sendMessage(request.getChatId(), "Ви вже проходили це опитування!");
                return emptyTextHandler;
            }
            if (answeredOptions.isEmpty()) {
                for (int i = 0; i < questions.size(); i++) answeredOptions.add(AnsweredOptions.builder().build());
            }
            int countValue = counter.get(request.getChatId());
            if (questions.size() > countValue) {
                Question question = questions.get(countValue);
                List<Option> options = optionsRepo.findByQuestion(question);
                if (countValue > 0) {
                    Question previous = questions.get(countValue - 1);
                    List<Option> previousOptions = optionsRepo.findByQuestion(previous);
                    int picked = Integer.parseInt(request.getUpdate().getMessage().getText());
                    AnsweredOptions answeredOption = answeredOptions.get(countValue - 1);
                    answeredOption.setOption(previousOptions.get(picked - 1));
                    answeredOptions.remove(countValue - 1);
                    Users user = userService.getByUsername(request.getUpdate().getMessage().getChat().getUserName());
                    if(user == null){
                        user = userService.getByUsername(request.getUpdate().getMessage().getChat().getFirstName());
                    }
                    answeredOption.setUser(user);
                    answeredOptions.add(countValue - 1, answeredOption);
                }
                StringBuilder stringBuilder = new StringBuilder();
                int optionNum = 1;
                List<String> rows = new ArrayList<>();
                for (Option option : options) {
                    stringBuilder.append("( ").append(optionNum).append(")").append(": ");
                    stringBuilder.append(option.getOptionText());
                    stringBuilder.append("\n");
                    rows.add(String.valueOf(optionNum));
                    optionNum++;
                }
                telegramBotService.sendMessage(request.getChatId(), question.getQuestionName() + "\n" + question.getQuestionText() + "\n" + stringBuilder.toString(),
                        ReplyKeyboardSender.buildMainMenu(rows));
                countValue++;
                counter.put(request.getChatId(), countValue);

            } else if (questions.size() == countValue) {
                Question question = questions.get(countValue - 1);
                List<Option> options = optionsRepo.findByQuestion(question);
                int picked = Integer.parseInt(request.getUpdate().getMessage().getText());
                AnsweredOptions answeredOption = answeredOptions.get(countValue - 1);
                answeredOption.setOption(options.get(picked - 1));
                answeredOptions.remove(countValue - 1);
                Users user = userService.getByUsername(request.getUpdate().getMessage().getChat().getUserName());
                if(user == null){
                    user = userService.getByUsername(request.getUpdate().getMessage().getChat().getFirstName());
                }
                answeredOption.setUser(user);
                answeredOptions.add(countValue - 1, answeredOption);
                counter.put(request.getChatId(), 0);
                currentSurveys.put(request.getChatId(), null);
                answeredOptionsRepo.saveAll(answers.get(request.getChatId()));
                answers.put(request.getChatId(), new ArrayList<AnsweredOptions>());
                telegramBotService.sendMessage(request.getChatId(), "Опитування завершено!");
                return emptyTextHandler;
            } else {
                telegramBotService.sendMessage(request.getChatId(), "Опитування уже завершено!");
                return emptyTextHandler;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return this;
    }

    public void putSurvey(Quiz survey, Long chatId) {
        currentSurveys.put(chatId, survey);
        answers.put(chatId, new ArrayList<AnsweredOptions>());
        counter.put(chatId, 0);
    }
}
