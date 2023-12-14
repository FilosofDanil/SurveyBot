package com.example.opituvalnik.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Table(name = "options")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "option_text")
    @NotBlank
    private String optionText;
    @ManyToOne()
    @JoinColumn(name = "question_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Question question;
    @OneToMany(mappedBy = "option", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<AnsweredOptions> answeredOptions;
}
