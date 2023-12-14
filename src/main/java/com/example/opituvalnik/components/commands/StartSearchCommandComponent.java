package com.example.opituvalnik.components.commands;

import com.example.opituvalnik.components.GlobalTextHandler;
import com.example.opituvalnik.components.StateManager;
import com.example.opituvalnik.components.keyboardsender.ReplyKeyboardSender;
import com.example.opituvalnik.components.texthandlers.SearchingProcessHandler;
import com.example.opituvalnik.enums.State;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.handlers.RequestHandler;
import com.example.telelibrary.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Log4j2
public class StartSearchCommandComponent extends RequestHandler {
    private final StateManager stateManager;

    private final TelegramBotService telegramService;

    private static final String command = "/search";

    @Override
    public boolean isApplicable(UserRequest request) {
        return isCommand(request.getUpdate(), command);
    }

    @Override
    public void handle(UserRequest request) {
        stateManager.setState(State.SEARCHING);
        telegramService.sendMessage(request.getChatId(), "Починаємо пошук опитувань \uD83D\uDD0D. Ви можете вибрати подане опитування натиснувши \uD83D\uDC4D, якщо же бажаєте продовжити пошук далі натискайте \uD83D\uDC4E. \n " +
                "Також можна знайти опитування по імені, натиснувши \uD83D\uDD0E. Якщо хочете завершити пошук, натисніть \uD83D\uDCA4. +\n Щоб почати пошук натисніть \uD83D\uDE80", ReplyKeyboardSender.buildMainMenu(List.of("\uD83D\uDE80")));
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
