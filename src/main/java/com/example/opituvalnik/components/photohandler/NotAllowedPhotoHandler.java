package com.example.opituvalnik.components.photohandler;

import com.example.opituvalnik.components.PhotoHandler;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("notAllowed")
@RequiredArgsConstructor
public class NotAllowedPhotoHandler implements PhotoHandler {
    private final TelegramBotService telegramBotService;

    @Override
    public PhotoHandler handle(UserRequest request) {
        telegramBotService.sendMessage(request.getChatId(), "Немає такого варіанту відповіді");
        return this;
    }
}
