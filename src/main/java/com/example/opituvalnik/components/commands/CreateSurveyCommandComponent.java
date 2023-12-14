package com.example.opituvalnik.components.commands;

import com.example.opituvalnik.components.StateManager;
import com.example.opituvalnik.enums.State;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.handlers.RequestHandler;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateSurveyCommandComponent extends RequestHandler {
    private final static String command = "/create_survey";

    private final StateManager stateManager;

    private final TelegramBotService telegramBotService;

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        telegramBotService.sendMessage(request.getChatId(), "Введіть назву вашого опитування!");
        stateManager.setState(State.CREATE_SURVEY);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
