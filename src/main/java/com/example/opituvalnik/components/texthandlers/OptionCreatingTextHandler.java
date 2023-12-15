package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.entities.Option;
import com.example.opituvalnik.entities.Question;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.repositories.OptionsRepo;
import com.example.opituvalnik.repositories.QuestionRepo;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OptionCreatingTextHandler implements TextHandler {
    private final Map<Long, Quiz> pickedSurveyMap;

    private final Map<Long, Question> alteredQuestionMap;

    private final QuestionRepo questionRepo;

    private final OptionsRepo optionsRepo;

    private final TelegramBotService telegramBotService;

    @Override
    public TextHandler handle(UserRequest request) {
        List<Option> options = new ArrayList<>();
        Question question = questionRepo.save(alteredQuestionMap.get(request.getChatId()));
        List<String> stringOptions = divideString(request.getUpdate().getMessage().getText());
        stringOptions.forEach(s -> {
            options.add(Option.builder()
                    .question(question)
                    .optionText(s)
                    .build());
        });
        optionsRepo.saveAll(options);
        telegramBotService.sendMessage(request.getChatId(), "Успішно створено!");
        return null;
    }

    public void putSurveyInMap(Quiz survey, Long id) {
        pickedSurveyMap.put(id, survey);
    }

    public void putQuestionInMap(Question question, Long id) {
        alteredQuestionMap.put(id, question);
    }

    private List<String> divideString(String input) {
        return Arrays.stream(input.split("[,\\s]+"))
                .collect(Collectors.toList());
    }
}
