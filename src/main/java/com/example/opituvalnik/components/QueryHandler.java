package com.example.opituvalnik.components;

import com.example.telelibrary.entities.telegram.UserRequest;

public interface QueryHandler {
    QueryHandler handle(UserRequest request);
}
