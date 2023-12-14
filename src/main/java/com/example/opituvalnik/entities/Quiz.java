package com.example.opituvalnik.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Table(name = "quizes")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "survey_name")
    @NotBlank
    private String surveyName;
    @Column(name = "survey_image")
    @NotBlank
    private String surveyImage;
    @Column(name = "survey_description")
    @NotBlank
    private String surveyDescription;
    @Column(name = "questionCount")
    @NotNull
    private Integer questionsCount;
    @Column(name = "published")
    @NotNull
    private Boolean published;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    @NotNull
    private Users user;
    @OneToMany(mappedBy = "quiz", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Question> questions;
}
