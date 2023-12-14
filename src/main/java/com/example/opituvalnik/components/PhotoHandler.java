package com.example.opituvalnik.components;

import com.example.telelibrary.entities.telegram.UserRequest;

public interface PhotoHandler {
    PhotoHandler handle(UserRequest request);
}
