package com.example.opituvalnik.components.queryhandlers;

import com.example.opituvalnik.components.QueryHandler;
import com.example.opituvalnik.components.StateManager;
import com.example.opituvalnik.components.keyboardsender.InlineKeyboardSender;
import com.example.opituvalnik.components.texthandlers.QuestionNameHandler;
import com.example.opituvalnik.entities.Question;
import com.example.opituvalnik.entities.Quiz;
import com.example.opituvalnik.enums.State;
import com.example.opituvalnik.repositories.QuestionRepo;
import com.example.opituvalnik.repositories.QuizRepo;
import com.example.opituvalnik.services.TelegramBotService;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("questionMenu")
@RequiredArgsConstructor
@Log4j2
public class QuestionsHandler implements QueryHandler {
    private final Map<Long, Quiz> pickedSurveyMap;

    private final QuestionNameHandler questionNameHandler;

    private final QuizRepo quizRepo;

    private final StateManager stateManager;

    private final TelegramBotService telegramBotService;

    private final QuestionRepo questionRepo;

    private final EmptyCallBackQuery emptyCallBackQuery;

    @Override
    public QueryHandler handle(UserRequest request) {
        String message = request.getUpdate().getCallbackQuery().getData();
        Quiz survey = pickedSurveyMap.get(request.getChatId());
        if (message.equals("Завершити")) {
            List<Question> questions = questionRepo.findByQuiz(survey);
            if (questions.size() == survey.getQuestionsCount()) {
                survey.setPublished(true);
                quizRepo.save(survey);
                telegramBotService.sendMessage(request.getChatId(), "Ваше опитування створено! Тепер воно доступне усім користувачам.");
                return emptyCallBackQuery;
            } else {
                telegramBotService.sendMessage(request.getChatId(), "Ви ще не завершили заповнення вашого опитування(не всі питання готові");
                return this;
            }

        } else {
            try{
                int num = Integer.parseInt(message);
                List<Question> questions = questionRepo.findByQuizAndQuestionNum(survey.getId(), num);
                if (!questions.isEmpty()) {
                    List<String> rows = new ArrayList<>();
                    for (int i = 0; i < survey.getQuestionsCount(); i++) {
                        rows.add(String.valueOf(i + 1));
                    }
                    rows.add("Завершити");
                    stateManager.setState(State.MENU);
                    telegramBotService.sendMessage(request.getChatId(), "Ви вже заповнили це питання!");
                    telegramBotService.sendMessage(request.getChatId(), "Для того щоб завершити створення опитування вам необхідно створити та заповнити всі питання опитування. Питань у вашому опитуванні: " + survey.getQuestionsCount(), InlineKeyboardSender.buildInlineKeyboard(rows, false));
                    return this;
                } else {
                    questionNameHandler.setNumberOfQuestion(num);
                    questionNameHandler.putSurveyInMap(survey, request.getChatId());
                    stateManager.setState(State.QUESTION_CREATION);
                    telegramBotService.sendMessage(request.getChatId(), "Надішліть будь-ласка запитання або речення, на яке треба дати відповідь.");
                    return this;
                }
            } catch (NumberFormatException e){
                log.error(e.getMessage());
                telegramBotService.sendMessage(request.getChatId(), "Немає такого вибору!");
                return this;
            }
        }
    }

    public void putSurveyInMap(Quiz survey, Long id) {
        pickedSurveyMap.put(id, survey);
    }
}
