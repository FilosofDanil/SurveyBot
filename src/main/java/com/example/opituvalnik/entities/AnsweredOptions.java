package com.example.opituvalnik.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Table(name = "answered_options")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnsweredOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "option_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Option option;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Users user;
}
