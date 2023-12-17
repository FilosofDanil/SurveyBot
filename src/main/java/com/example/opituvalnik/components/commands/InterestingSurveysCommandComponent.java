package com.example.opituvalnik.components.commands;

import com.example.opituvalnik.components.StateManager;
import com.example.opituvalnik.components.keyboardsender.ReplyKeyboardSender;
import com.example.opituvalnik.components.texthandlers.PopularSurveyTextHandler;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.enums.State;
import com.example.opituvalnik.requesthandler.RequestHandler;
import com.example.opituvalnik.services.*;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class InterestingSurveysCommandComponent extends RequestHandler {
    private static final String command = "/popular";

    private final QuizService quizService;

    private final StartSurveyService startSurveyService;

    private final PopularSurveyTextHandler popularSurveyTextHandler;

    private final StateManager stateManager;

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @SneakyThrows
    @Override
    public void handle(UserRequest request) {
        Quiz survey = quizService.findPopular();
        startSurveyService.startSurvey(survey, request);
        popularSurveyTextHandler.putSurvey(survey, request.getChatId());
        stateManager.setState(State.POPULAR);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
