package com.example.opituvalnik.components;

import com.example.telelibrary.entities.telegram.UserRequest;

import java.net.MalformedURLException;

public interface TextHandler {
    TextHandler handle(UserRequest request);
}
