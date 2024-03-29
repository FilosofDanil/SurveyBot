package com.example.opituvalnik.components.texthandlers;

import com.example.opituvalnik.components.TextHandler;
import com.example.opituvalnik.entities.Question;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("questionCreator")
@RequiredArgsConstructor
public class QuestionNameHandler implements TextHandler {
    private final Map<Long, Quiz> pickedSurveyMap;

    private final TelegramBotService telegramBotService;

    private final OptionCreatingTextHandler optionHandler;

    private int numberOfQuestion = 0;

    @Override
    public TextHandler handle(UserRequest request) {
        String name = request.getUpdate().getMessage().getText();
        Long id = request.getChatId();
        Quiz survey = pickedSurveyMap.get(id);
        Question question = Question.builder()
                .questionName("Питання #" + numberOfQuestion)
                .questionNum(numberOfQuestion)
                .quiz(survey)
                .questionText(name)
                .build();
        telegramBotService.sendMessage(id, "Добре! Далі надішлість будь ласка опції(для вибору) у форматі:\n" +
                "option1, option2, oprion3 або через пробіл option1 option2 option3\n" +
                "Наприклад: \n" +
                "Запитання (Рим столиця якох країни?)\n" +
                "Франція, Італія, Австрія або Франція Італія Австрія");
        optionHandler.putSurveyInMap(survey, id);
        optionHandler.putQuestionInMap(question, id);
        return optionHandler;

    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public void putSurveyInMap(Quiz survey, Long id) {
        pickedSurveyMap.put(id, survey);
    }


}
