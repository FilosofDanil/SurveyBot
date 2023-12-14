package com.example.opituvalnik.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Table(name = "users")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tg_name")
    @NotBlank
    private String tgName;
    @Column(name = "username")
    @NotNull
    private String username;
    @Column(name = "chat_id")
    @NotNull
    private Long chatId;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Quiz> quizzes;
    @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<AnsweredOptions> answeredOptions;
}
