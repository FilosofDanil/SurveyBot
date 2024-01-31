package com.example.opituvalnik.components;

import com.example.opituvalnik.requesthandler.RequestHandler;
import com.example.telelibrary.entities.telegram.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GlobalTextHandler extends RequestHandler {
    private TextHandler statefulHandler;

    @Autowired
    public GlobalTextHandler(@Qualifier("empty") TextHandler statefulHandler) {
        this.statefulHandler = statefulHandler;
    }

    public void setStatefulHandler(TextHandler statefulHandler) {
        this.statefulHandler = statefulHandler;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isTextMessage(request.getUpdate());
    }

    @Override
    public void handle(UserRequest request) {
        statefulHandler = statefulHandler.handle(request);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
