package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.repositories.QuizRepo;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("rightOrWrong")
@RequiredArgsConstructor
public class RightOrWrong implements TextHandler {
    private final QuizRepo quizRepo;

    private final TelegramBotService telegramBotService;

    private Quiz survey;

    public void setSurvey(Quiz survey) {
        this.survey = survey;
    }

    @Override
    public TextHandler handle(UserRequest request) {
        String message = request.getUpdate().getMessage().getText();
        if(message.equals("\uD83D\uDC4D")){
            quizRepo.save(survey);
            telegramBotService.sendMessage(request.getChatId(), "Ваше опитування збережено! Далі, для того аби опитування стало доступним для користувачів, необхідно завершити його створення(заповнити всі питання). Для цього вам необхідно перейти до ваших опитувань за допомогою команди /my_surveys і там вибрати опитування(незаповнені опитування будуть відмічені відповідним чином)");
            return null;
        } else if(message.equals("\uD83D\uDC4E")){
            telegramBotService.sendMessage(request.getChatId(), "Введіть, що хочете змінити! \n");
            return null;
        } else {
            telegramBotService.sendMessage(request.getChatId(), "Нема такого варіанту!");
            return this;
        }
    }
}
