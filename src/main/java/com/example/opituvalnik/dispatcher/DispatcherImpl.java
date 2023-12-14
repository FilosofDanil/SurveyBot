package com.example.opituvalnik.dispatcher;

import com.example.opituvalnik.entities.Users;
import com.example.opituvalnik.services.UserService;
import com.example.telelibrary.dispatcher.Dispatcher;
import com.example.telelibrary.dispatcher.impl.DispatcherBean;
import com.example.telelibrary.entities.telegram.UserRequest;
import com.example.telelibrary.handlers.RequestHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class DispatcherImpl extends DispatcherBean implements Dispatcher {
    private final UserService userService;

    public DispatcherImpl(List<RequestHandler> handlers, UserService userService) {
        super(handlers);
        this.userService = userService;
    }

    @Override
    public void dispatch(UserRequest userRequest) {
        super.dispatch(userRequest);
        createUser(userRequest);
    }

    private void createUser(UserRequest request) {
        try {
            if (request.getUpdate().getMessage() == null){
                return;
            }
            if (request.getUpdate().getMessage().getText() == null){
                return;
            }
            String username = request.getUpdate().getMessage().getChat().getUserName();
            String tgName = request.getUpdate().getMessage().getChat().getFirstName();
            if (username == null || username.isBlank()) {
                username = tgName;
            }

            if (request.getUpdate().getMessage().getText().equals("/start") && userService.getByUsername(username) != null) {
                log.info("User just restarted bot.");
            } else if (userService.getByUsername(username) == null) {
                Users user = userService.createUser(Users.builder()
                        .chatId(request.getChatId())
                        .username(username)
                        .tgName(tgName)
                        .build());
                log.info("Registered new user: " + user.getUsername());
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        }
    }
}
