package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.components.keyboardsender.ReplyKeyboardSender;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.repositories.QuizRepo;
import com.example.opituvalnik.services.PhotoSender;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("searching")
@RequiredArgsConstructor
@Log4j2
public class SearchingProcessHandler implements TextHandler {
    private final Map<Long, Integer> userTempMap = new HashMap<>();

    private final QuizRepo quizRepo;

    private final PhotoSender photoSender;

    private final TelegramBotService telegramBotService;

    private final EmptyTextHandler emptyTextHandler;

    private final StartedCertainSurvey startedSurvey;

    @SneakyThrows
    @Override
    public TextHandler handle(UserRequest request) {
        if (!userTempMap.containsKey(request.getChatId())) userTempMap.put(request.getChatId(), 0);
        List<Quiz> surveys = quizRepo.findAll().stream().filter(Quiz::getPublished).toList();
        int position = userTempMap.get(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("\uD83D\uDC4E") || message.equals("\uD83D\uDE80")) {
            if (position == surveys.size()) {
                telegramBotService.sendMessage(request.getChatId(), "Опитувань більше немає :). Ви можете почати пошук заново, або почекати, поки з'являться нові опитування.");
                return this;
            }
            if (surveys.size() > position) {
                Quiz survey = surveys.get(position);
                URL urlObj = new URL(survey.getSurveyImage());
                try (InputStream is = urlObj.openStream()) {
                    byte[] stream = is.readAllBytes();
                    FileOutputStream outputStream = new FileOutputStream("lol.jpg");
                    outputStream.write(stream);
                    outputStream.close();
                } catch (IOException e) {
                    log.error("Something went wrong with files " + e.getMessage());
                }
                List<String> rows = List.of("\uD83D\uDC4D", "\uD83D\uDC4E", "\uD83D\uDD0E", "\uD83D\uDCA4");
                photoSender.sendPhoto(request.getChatId(), "lol.jpg", survey.getSurveyName() + "\n" + "Опис: " + survey.getSurveyDescription() + "\n\n" + "К-сть питань:" + survey.getQuestionsCount(),
                        ReplyKeyboardSender.buildMainMenu(rows));
                position++;
            } else {
                telegramBotService.sendMessage(request.getChatId(), "Опитувань більше немає :). Ви можете почати пошук заново, або почекати, поки з'являться нові опитування.");
            }
        } else if (message.equals("\uD83D\uDC4D")) {
            telegramBotService.sendMessage(request.getChatId(), "Починаємо проходження даного опитування");
            startedSurvey.putSurvey(surveys.get(position - 1), request.getChatId());
            return startedSurvey.handle(request);
        } else if (message.equals("\uD83D\uDD0E")) {
            telegramBotService.sendMessage(request.getChatId(), "Функція поки недоступна");
            position--;
        } else if (message.equals("\uD83D\uDCA4")) {
            position--;
            if (position >= 0) userTempMap.put(request.getChatId(), position);
            telegramBotService.sendMessage(request.getChatId(), "Завершуємо пошук опитувань...");
            return emptyTextHandler;
        } else {
            telegramBotService.sendMessage(request.getChatId(), "Нема такого варіанту відповіді");
        }
        userTempMap.put(request.getChatId(), position);
        return this;
    }
}
