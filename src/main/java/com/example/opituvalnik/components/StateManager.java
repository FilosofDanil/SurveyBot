package com.example.opituvalnik.components;

import com.example.opituvalnik.enums.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class StateManager {
    private final GlobalTextHandler globalTextHandler;

    private final GlobalQueryHandler globalQueryHandler;

    private final GlobalPhotoHandler globalPhotoHandler;

    private final ApplicationContext context;

    public void setState(State state) {
        if (state.equals(State.EMPTY)) globalTextHandler
                .setStatefulHandler(context
                        .getBean("empty", TextHandler.class));
        else if (state.equals(State.SEARCHING)) globalTextHandler.setStatefulHandler(context
                .getBean("searching", TextHandler.class));
        else if (state.equals(State.CREATE_SURVEY)) globalTextHandler.setStatefulHandler(context
                .getBean("createSurvey", TextHandler.class));
        else if (state.equals(State.SURVEY_PHOTO)) {
            globalPhotoHandler.setStatefulHandler(context
                    .getBean("surveyPhoto", PhotoHandler.class));
        } else if (state.equals(State.RIGHT_WRONG)) {
            globalTextHandler.setStatefulHandler(context
                    .getBean("rightOrWrong", TextHandler.class));
        } else if (state.equals(State.ADD_QUESTIONS)) {
            globalQueryHandler.setQueryHandler(context.getBean("finishCreatingSurvey",
                    QueryHandler.class));
        } else if (state.equals(State.QUESTION_CREATION)) {
            globalTextHandler.setStatefulHandler(context
                    .getBean("questionCreator", TextHandler.class));
        } else if (state.equals(State.MENU)) {
            globalQueryHandler.setQueryHandler(context
                    .getBean("questionMenu", QueryHandler.class));
        } else if (state.equals(State.POPULAR)) {
            globalTextHandler.setStatefulHandler(context
                    .getBean("popularText", TextHandler.class));
        }
    }
}
