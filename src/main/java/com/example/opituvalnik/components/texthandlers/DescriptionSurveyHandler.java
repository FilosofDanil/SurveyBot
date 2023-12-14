package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.entities.Quiz;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.text.TabExpander;

@Component("descriptionSurvey")
@RequiredArgsConstructor
public class DescriptionSurveyHandler implements TextHandler {
    private Quiz survey;

    private final TelegramBotService telegramBotService;

    private final QuestionsSurveyHandler questionsSurveyHandler;

    public void setSurvey(Quiz survey) {
        this.survey = survey;
    }

    @Override
    public TextHandler handle(UserRequest request) {
        String msg = request.getUpdate().getMessage().getText();
        if (msg != null && !msg.isBlank()) {
            telegramBotService.sendMessage(request.getChatId(), "Добре! Далі введіть к-сть питань у вашому опитуванні.");
            survey.setSurveyDescription(msg);
            questionsSurveyHandler.setSurvey(survey);
            return questionsSurveyHandler;
        } else {
            telegramBotService.sendMessage(request.getChatId(), "Будь-ласка введіть повторно!");
            return this;
        }
    }
}
