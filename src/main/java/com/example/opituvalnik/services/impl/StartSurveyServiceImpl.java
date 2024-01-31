package com.example.opituvalnik.services.impl;

import com.example.opituvalnik.components.keyboardsender.ReplyKeyboardSender;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.services.PhotoSender;
import com.example.opituvalnik.services.StartSurveyService;
import com.example.opituvalnik.services.StatisticService;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class StartSurveyServiceImpl implements StartSurveyService {
    private final TelegramBotService telegramBotService;

    private final PhotoSender photoSender;

    private final StatisticService statisticService;

    @Override
    public void startSurvey(Quiz survey, UserRequest request) throws MalformedURLException {
//        URL urlObj = new URL(survey.getSurveyImage());
//        try (InputStream is = urlObj.openStream()) {
//            byte[] stream = is.readAllBytes();
//            FileOutputStream outputStream = new FileOutputStream("src/main/resources/" + path);
//            outputStream.write(stream);
//            outputStream.close();
//        } catch (IOException e) {
//            log.error("Something went wrong with files " + e.getMessage());
//        }
        List<String> rows = List.of("\uD83D\uDC4D", "\uD83D\uDC4E", "\uD83D\uDD0E", "\uD83D\uDCA4");
        photoSender.sendPhoto(request.getChatId(), survey.getSurveyImage(), survey.getSurveyName()
                        + "\n" + "Опис: " + survey.getSurveyDescription()
                        + "\n\n" + "К-сть питань:" + survey.getQuestionsCount()
                        +"\n Користувачів пройшло опитування: " + statisticService.passedTimes(survey),
                ReplyKeyboardSender.buildMainMenu(rows));
    }
}
