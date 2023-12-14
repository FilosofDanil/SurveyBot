package com.example.opituvalnik.repositories;

import com.example.opituvalnik.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

public interface UserRepo extends CrudRepository<Users, Long> {
    List<Users> findAll();

    Users findByUsername(String username);
}
