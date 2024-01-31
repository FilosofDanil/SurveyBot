package com.example.opituvalnik.dispatcher;

import com.example.opituvalnik.requesthandler.RequestHandler;
import com.example.telelibrary.entities.telegram.UserRequest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DispatcherBean implements Dispatcher {

    private final List<RequestHandler> handlers;

    public DispatcherBean(List<RequestHandler> handlers) {
        this.handlers = handlers
                .stream()
                .sorted(Comparator
                        .comparing(RequestHandler::isGlobal)
                        .reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void dispatch(UserRequest userRequest) {
        for (RequestHandler userRequestHandler : handlers) {
            if (userRequestHandler.isApplicable(userRequest)) {
                userRequestHandler.handle(userRequest);
                return;
            }
        }
    }
}
