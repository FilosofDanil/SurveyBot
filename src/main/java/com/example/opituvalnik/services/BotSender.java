package com.example.opituvalnik.services;

import com.example.telelibrary.configs.BotConfig;
import org.springframework.stereotype.Service;

@Service
public class BotSender extends com.example.telelibrary.services.bot.BotSender {
    protected BotSender(BotConfig config) {
        super(config);
    }
}
