package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.entities.AnsweredOptions;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.services.StartSurveyService;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component("popularText")
@RequiredArgsConstructor
public class PopularSurveyTextHandler implements TextHandler {
    private final StartSurveyService startSurveyService;

    private final EmptyTextHandler emptyTextHandler;

    private final TelegramBotService telegramBotService;

    private final Map<Long, Quiz> currentSurveys = new HashMap<>();

    private final StartedCertainSurvey startedSurvey;

    @Override
    public TextHandler handle(UserRequest request) {
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("\uD83D\uDC4E") || message.equals("\uD83D\uDE80")) {
            telegramBotService.sendMessage(request.getChatId(), "Завершуємо пошук опитувань...");
        } else if (message.equals("\uD83D\uDC4D")) {
            telegramBotService.sendMessage(request.getChatId(), "Починаємо проходження даного опитування");
            startedSurvey.putSurvey(currentSurveys.get(request.getChatId()), request.getChatId());
            return startedSurvey.handle(request);
        } else if (message.equals("\uD83D\uDD0E")) {
            telegramBotService.sendMessage(request.getChatId(), "Функція поки недоступна");
        } else if (message.equals("\uD83D\uDCA4")) {
            telegramBotService.sendMessage(request.getChatId(), "Завершуємо пошук опитувань...");
            return emptyTextHandler;
        } else {
            telegramBotService.sendMessage(request.getChatId(), "Нема такого варіанту відповіді");
        }
        return emptyTextHandler;
    }

    public void putSurvey(Quiz survey, Long chatId) {
        currentSurveys.put(chatId, survey);
    }
}
