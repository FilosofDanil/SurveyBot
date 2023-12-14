package com.example.opituvalnik.services.impl;

import com.example.opituvalnik.entities.Users;
import com.example.opituvalnik.repositories.UserRepo;
import com.example.opituvalnik.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public Users createUser(Users user) {
        return userRepo.save(user);
    }

    @Override
    public Users getByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
