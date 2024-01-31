package com.example.opituvalnik.configs;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
public class BotConfig {
    private String name;

    private String token;
}
