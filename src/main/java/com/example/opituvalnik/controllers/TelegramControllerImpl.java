package com.example.opituvalnik.controllers;

import com.example.opituvalnik.configs.BotConfig;
import com.example.opituvalnik.dispatcher.Dispatcher;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
public class TelegramControllerImpl extends TelegramController {

    public TelegramControllerImpl(Dispatcher dispatcher, BotConfig config) {
        super(dispatcher, config);
    }

    @Override
    public String getBotUsername() {
        return super.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return super.getBotToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        super.onUpdateReceived(update);
    }

    @Override
    public void sendMessage(SendMessage message) {
        super.sendMessage(message);
    }
}
