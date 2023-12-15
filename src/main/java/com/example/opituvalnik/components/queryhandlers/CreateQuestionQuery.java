package com.example.opituvalnik.components.queryhandlers;

import com.example.opituvalnik.components.QueryHandler;
import com.example.opituvalnik.components.keyboardsender.InlineKeyboardSender;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.repositories.QuizRepo;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("finishCreatingSurvey")
@RequiredArgsConstructor
public class CreateQuestionQuery implements QueryHandler {
    private final QuizRepo quizRepo;

    private final MenuHandler menuHandler;

    private final TelegramBotService telegramBotService;

    @Override
    public QueryHandler handle(UserRequest request) {
        long id = Long.parseLong(request.getUpdate().getCallbackQuery().getData());
        if (quizRepo.findById(id).isEmpty()){
            telegramBotService.sendMessage(request.getChatId(), "Не існує!");
            return this;
        }
        Quiz survey = quizRepo.findById(id).get();
        List<String> rows = List.of("Завершити створення", "Видалити опитування", "Редагувати");
        Map<String, String> rowMap = new HashMap<>();
        rows.forEach(row -> {
            rowMap.put(row, row);
        });
        menuHandler.putSurveyInMap(survey, request.getChatId());
        telegramBotService.sendMessage(request.getChatId(), "Виберіть з-поміж наступних пунктів:", InlineKeyboardSender.buildInlineKeyboard(rows, false, rowMap));
        return menuHandler;
    }
}
