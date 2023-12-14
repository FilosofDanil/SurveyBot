package com.example.opituvalnik.requesthandler;

import com.example.telelibrary.handlers.RequestHandler;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class ExtendedRequestHandler extends RequestHandler {
    public boolean isPhotoMessage(Update update) {
        return update.hasMessage() && update.getMessage().hasPhoto();
    }
}
