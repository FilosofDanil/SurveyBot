package com.example.opituvalnik.services;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface PhotoSender {
    void sendPhoto(Long chatId, String path, String caption);

    void sendPhoto(Long chatId, String path, String caption, ReplyKeyboardMarkup markup);
}
