package com.example.opituvalnik.services.impl;

import com.example.opituvalnik.services.PhotoSender;
import com.example.telelibrary.services.bot.BotSender;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.io.File;

@Service
@Log4j2
public class TelegramServiceBean extends TelegramBotService implements PhotoSender {
    private final BotSender botSender;

    public TelegramServiceBean(BotSender botSender) {
        super(botSender);
        this.botSender = botSender;
    }

    @Override
    public void sendPhoto(Long chatId, String path, String caption) {
        try {
            this.botSender.execute(getSendMessage(chatId, path, caption));
        } catch (Exception var3) {
            log.error(var3.getMessage());
        }
    }

    @Override
    public void sendPhoto(Long chatId, String path, String caption, ReplyKeyboardMarkup markup) {
        try {
            SendPhoto sendMessage = (getSendMessage(chatId, path, caption));
            sendMessage.setReplyMarkup(markup);
            this.botSender.execute(sendMessage);
        } catch (Exception var3) {
            log.error(var3.getMessage());
        }
    }

    private SendPhoto getSendMessage(Long chatId, String path, String caption) {
        InputFile file = new InputFile(new File(path));
        SendPhoto sendMessage = new SendPhoto();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setPhoto(file);
        sendMessage.setCaption(caption);
        return sendMessage;
    }
}
