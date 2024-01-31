package com.example.opituvalnik.configs;

import com.example.opituvalnik.controllers.TelegramController;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Configuration {
    @Bean
    public TelegramBotsApi telegramBotsApi(TelegramController telegramController) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(telegramController);
        return api;
    }
}

