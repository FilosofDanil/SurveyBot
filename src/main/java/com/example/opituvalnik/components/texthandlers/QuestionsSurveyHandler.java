package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.StateManager;
import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.components.photohandler.LoadPhotoForSurveyHandler;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.enums.State;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component("questionsSurvey")
@RequiredArgsConstructor
@Log4j2
public class QuestionsSurveyHandler implements TextHandler {
    private Quiz survey;

    private final TelegramBotService telegramBotService;

    private final StateManager stateManager;

    private final EmptyTextHandler emptyTextHandler;

    private final LoadPhotoForSurveyHandler loadPhotoForSurveyHandler;

    public void setSurvey(Quiz survey) {
        this.survey = survey;
    }

    @Override
    public TextHandler handle(UserRequest request) {
        String msg = request.getUpdate().getMessage().getText();
        if (msg != null && !msg.isBlank()) {
            try {
                survey.setQuestionsCount(Integer.parseInt(msg));
                log.info(survey);
                telegramBotService.sendMessage(request.getChatId(), "Тепер надішліть будь-ласка зображення для вашого опитування.");
                loadPhotoForSurveyHandler.setSurvey(survey);
                stateManager.setState(State.SURVEY_PHOTO);
                return emptyTextHandler;
            } catch (NumberFormatException e){
                log.error(e.getMessage());
                telegramBotService.sendMessage(request.getChatId(), "Введіть будь ласка число!");
                return this;
            }
        } else {
            telegramBotService.sendMessage(request.getChatId(), "Будь-ласка введіть повторно!");
            return this;
        }
    }
}
