package com.example.opituvalnik.components;

import com.example.opituvalnik.requesthandler.ExtendedRequestHandler;
import com.example.telelibrary.entities.telegram.UserRequest;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class GlobalPhotoHandler extends ExtendedRequestHandler {
    private PhotoHandler statefulHandler;

    @Autowired
    public GlobalPhotoHandler(@Qualifier("notAllowed") PhotoHandler statefulHandler) {
        this.statefulHandler = statefulHandler;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isPhotoMessage(request.getUpdate());
    }

    @SneakyThrows
    @Override
    public void handle(UserRequest request) {
        statefulHandler = statefulHandler.handle(request);
    }

    public void setStatefulHandler(PhotoHandler statefulHandler) {
        this.statefulHandler = statefulHandler;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
