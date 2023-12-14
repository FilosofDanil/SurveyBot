package com.example.opituvalnik.controllers;

import com.example.telelibrary.configs.BotConfig;
import com.example.telelibrary.controllers.TelegramController;
import com.example.telelibrary.dispatcher.Dispatcher;
import com.example.telelibrary.services.bot.BotSender;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.client.methods.HttpHead;
import org.glassfish.grizzly.http.HttpHeader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.net.URL;
import java.util.List;

@Controller
public class TelegramControllerImpl extends TelegramController {
    public TelegramControllerImpl(Dispatcher dispatcher, BotConfig config) {
        super(dispatcher, config);
    }

    @Override
    public String getBotUsername() {
        System.out.println();
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
