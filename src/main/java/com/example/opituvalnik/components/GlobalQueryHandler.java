package com.example.opituvalnik.components;

import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.handlers.RequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GlobalQueryHandler extends RequestHandler {
    private QueryHandler queryHandler;

    @Autowired
    public GlobalQueryHandler(@Qualifier("emptyQuery") QueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    @Override
    public boolean isApplicable(UserRequest request) {
        return isBackQuery(request.getUpdate());
    }

    @Override
    public void handle(UserRequest request) {
        queryHandler = queryHandler.handle(request);
    }

    public void setQueryHandler(QueryHandler queryHandler) {
        this.queryHandler = queryHandler;
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
