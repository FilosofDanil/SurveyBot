package com.example.opituvalnik.services;

import com.example.opituvalnik.configs.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Slf4j
@Service
public class BotSender extends DefaultAbsSender {

    private final BotConfig config;

    protected BotSender(BotConfig config) {
        super(new DefaultBotOptions());
        this.config = config;
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }
}
