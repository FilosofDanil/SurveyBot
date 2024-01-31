package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("empty")
@RequiredArgsConstructor
public class EmptyTextHandler implements TextHandler {
    private final TelegramBotService telegramBotService;

    @Override
    public TextHandler handle(UserRequest request) {
        telegramBotService.sendMessage(request.getChatId(), "Немає такого варіанту відповіді");
        return this;
    }
}
