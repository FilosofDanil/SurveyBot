package com.example.opituvalnik.components.queryhandlers;


import com.example.opituvalnik.components.QueryHandler;
import com.example.opituvalnik.components.keyboardsender.InlineKeyboardSender;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.repositories.QuizRepo;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MenuHandler implements QueryHandler {
    private final QuizRepo quizRepo;

    private final Map<Long, Quiz> pickedSurveyMap;

    private final QuestionsHandler questionsHandler;

    private final TelegramBotService telegramBotService;

    private final EmptyCallBackQuery emptyCallBackQuery;

    @Override
    public QueryHandler handle(UserRequest request) {
        Quiz survey = pickedSurveyMap.get(request.getChatId());
        if (request.getUpdate().getCallbackQuery().getData()
                .equals("Видалити опитування")) {
            quizRepo.deleteById(survey.getId());
            pickedSurveyMap.remove(request.getChatId());
            telegramBotService.sendMessage(request.getChatId(), "Ваше опитування було успішно видалено");
            return emptyCallBackQuery;
        } else if (request.getUpdate().getCallbackQuery().getData()
                .equals("Завершити створення")) {
            List<String> rows = new ArrayList<>();
            for (int i = 0; i < survey.getQuestionsCount(); i++) {
                rows.add(String.valueOf(i + 1));
            }
            questionsHandler.putSurveyInMap(survey, request.getChatId());
            pickedSurveyMap.remove(request.getChatId());
            rows.add("Завершити");
            telegramBotService.sendMessage(request.getChatId(), "Для того щоб завершити створення опитування вам необхідно створити та заповнити всі питання опитування. Питань у вашому опитуванні: " + survey.getQuestionsCount(), InlineKeyboardSender.buildInlineKeyboard(rows, false));
            return questionsHandler;
        } else {
            return this;
        }
    }

    public void putSurveyInMap(Quiz survey, Long id) {
        pickedSurveyMap.put(id, survey);
    }
}
