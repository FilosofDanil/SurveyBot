package com.example.opituvalnik.services;

import com.example.opituvalnik.entities.Users;
import org.telegram.telegrambots.meta.api.objects.User;

public interface UserService {
    Users createUser(Users user);

    Users getByUsername(String username);
}
