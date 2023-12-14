package com.example.opituvalnik.components.commands;

import com.example.opituvalnik.components.StateManager;
import com.example.opituvalnik.components.keyboardsender.InlineKeyboardSender;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.entities.Users;
import com.example.opituvalnik.enums.State;
import com.example.opituvalnik.repositories.QuizRepo;
import com.example.opituvalnik.services.UserService;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.handlers.RequestHandler;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MySurveysCommandComponent extends RequestHandler {
    private final TelegramBotService telegramBotService;

    private final QuizRepo quizRepo;

    private final UserService userService;

    private final StateManager stateManager;

    private static final String command = "/my_surveys";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        Users user = userService.getByUsername(request.getUpdate().getMessage().getChat().getUserName());
        List<Quiz> userSurveys = quizRepo.findByUser(user);
        stateManager.setState(State.ADD_QUESTIONS);
        List<String> rows = new ArrayList<>();
        Map<String, String> rowMap = new HashMap<>();
        userSurveys.forEach(quiz -> {
            rows.add(quiz.getSurveyName());
            rowMap.put(quiz.getSurveyName(), quiz.getId().toString());
        });
        if (rows.isEmpty()){
            telegramBotService.sendMessage(request.getChatId(), "Наразі ви створили жодного опитування!");
            return;
        }
        telegramBotService.sendMessage(request.getChatId(), "Ось ваші опитування, які були створені від вашого імені: \n", InlineKeyboardSender.buildInlineKeyboard(rows, false, rowMap));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
