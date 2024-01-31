package com.example.opituvalnik.components.photohandler;

import com.example.opituvalnik.components.PhotoHandler;
import com.example.opituvalnik.components.StateManager;
import com.example.opituvalnik.components.keyboardsender.ReplyKeyboardSender;
import com.example.opituvalnik.components.texthandlers.RightOrWrong;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.enums.State;
import com.example.opituvalnik.services.PhotoSender;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Component("surveyPhoto")
@Log4j2
public class LoadPhotoForSurveyHandler implements PhotoHandler {
    private Quiz survey;

    private final NotAllowedPhotoHandler notAllowedPhotoHandler;

    private final PhotoSender photoSender;

    private final TelegramBotService telegramBotService;

    private final StateManager stateManager;

    private final RightOrWrong rightOrWrong;

    @SneakyThrows
    @Override
    public PhotoHandler handle(UserRequest request) {
        Update update = request.getUpdate();
        List<PhotoSize> photoSizes = update.getMessage().getPhoto();
        String response = photoSizes.get(photoSizes.size() - 1).getFileId();
        //
        String url = "https://api.telegram.org/bot6867064283:AAH2CZ0fWq-KFKDdaATxHbOJa9r_dim3KJo/getFile?file_id=" + response;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpRequest = new HttpEntity<>(httpHeaders);
        JSONObject jsonObject = new JSONObject(restTemplate.exchange(url, HttpMethod.GET, httpRequest, String.class).getBody());
        String path = String.valueOf(jsonObject.getJSONObject("result").getString("file_path"));
        String downloadSource = "https://api.telegram.org/file/bot6867064283:AAH2CZ0fWq-KFKDdaATxHbOJa9r_dim3KJo/" + path;
        URL urlObj = new URL(downloadSource);
        try (InputStream is = urlObj.openStream()) {
            byte[] stream = is.readAllBytes();
            FileOutputStream outputStream = new FileOutputStream("src/main/resources/" + path);
            survey.setSurveyImage("src/main/resources/" + path);
            outputStream.write(stream);
            outputStream.close();
        } catch (IOException e) {
            log.error("Something went wrong with files " + e.getMessage());
        }
        List<String> rows = List.of("\uD83D\uDC4D", "\uD83D\uDC4E");
        photoSender.sendPhoto(request.getChatId(), "src/main/resources/" + path, survey.getSurveyName() + "\n" + "Опис: " + survey.getSurveyDescription() + "\n\n" + "К-сть питань:" + survey.getQuestionsCount(), ReplyKeyboardSender.buildMainMenu(rows));
        telegramBotService.sendMessage(request.getChatId(), "Все правильно?!");
        rightOrWrong.setSurvey(survey);
        stateManager.setState(State.RIGHT_WRONG);
        return notAllowedPhotoHandler;
    }

    public void setSurvey(Quiz survey) {
        this.survey = survey;
    }
}
