package com.example.opituvalnik.dispatcher;

import com.example.telelibrary.entities.telegram.UserRequest;

public interface Dispatcher {
    void dispatch(UserRequest request);
}
