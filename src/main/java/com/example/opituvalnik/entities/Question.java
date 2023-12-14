package com.example.opituvalnik.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Table(name = "questions")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "question_name")
    @NotBlank
    private String questionName;
    @Column(name = "question_text")
    @NotBlank
    private String questionText;
    @Column(name = "question_num")
    @NotBlank
    private Integer questionNum;
    @ManyToOne()
    @JoinColumn(name = "quiz_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Quiz quiz;
    @OneToMany(mappedBy = "question", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Option> options;
}
