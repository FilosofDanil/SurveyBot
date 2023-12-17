package com.example.opituvalnik.services;

import com.example.opituvalnik.entities.Quiz;
import com.example.telelibrary.entities.telegram.UserRequest;

import java.net.MalformedURLException;

public interface StartSurveyService {
    void startSurvey(Quiz survey, UserRequest request) throws MalformedURLException;
}
