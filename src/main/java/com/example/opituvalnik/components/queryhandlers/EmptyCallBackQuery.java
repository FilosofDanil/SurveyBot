package com.example.opituvalnik.components.queryhandlers;

import com.example.opituvalnik.components.QueryHandler;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("emptyQuery")
@RequiredArgsConstructor
public class EmptyCallBackQuery implements QueryHandler {
    private final TelegramBotService telegramBotService;

    @Override
    public QueryHandler handle(UserRequest request) {
        telegramBotService.sendMessage(request.getChatId(), "Нема такого варіанту відповіді!");
        return this;
    }
}
