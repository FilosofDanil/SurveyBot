package com.example.opituvalnik.components.commands;

import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.handlers.RequestHandler;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HelpCommandComponent extends RequestHandler {
    private final TelegramBotService telegramBotService;

    private static final String command = "/help";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        telegramBotService.sendMessage(request.getChatId(), "Бот для проходження опитувань. Якщо у вас є певні запитання, виберіть варіанти популярних запитань, або відправте своє");
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
