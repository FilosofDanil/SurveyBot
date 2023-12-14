package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.services.UserService;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("createSurvey")
@RequiredArgsConstructor
public class CreatingSurveyHandler implements TextHandler {
    private final TelegramBotService telegramBotService;

    private final DescriptionSurveyHandler descriptionSurveyHandler;

    private final UserService userService;

    @Override
    public TextHandler handle(UserRequest request) {
        String msg = request.getUpdate().getMessage().getText();
        if (msg != null && !msg.isBlank()) {
            Quiz survey = Quiz.builder()
                    .surveyName(msg)
                    .published(false)
                    .user(userService.getByUsername(request.getUpdate().getMessage().getChat().getUserName()))
                    .build();
            telegramBotService.sendMessage(request.getChatId(), "Придумайте опис для вашого опитування. Напишіть не більше 2-3 речень.");
            descriptionSurveyHandler.setSurvey(survey);
            return descriptionSurveyHandler;
        } else {
            telegramBotService.sendMessage(request.getChatId(), "Будь-ласка введіть повторно!");
            return this;
        }
    }
}
