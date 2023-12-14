package com.example.opituvalnik.components.commands;

import com.example.opituvalnik.components.GlobalTextHandler;
import com.example.opituvalnik.entities.Users;
import com.example.opituvalnik.services.UserService;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.handlers.RequestHandler;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class StartCommandComponent extends RequestHandler {

    private final TelegramBotService telegramService;

    private final GlobalTextHandler globalTextHandler;

    private final UserService userService;

    private static final String command = "/start";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
//        createUser(request);
        telegramService.sendMessage(request.getChatId(), "Вітаю! Це бот призначений для створення та проходження опитувань! \n" +
                "Для того аби пройти ваше перше опитування і отримати цікавий результат наприкінці - натисність команду для пошуку  /search ");
    }

    @Override
    public boolean isGlobal() {
        return true;
    }


}

