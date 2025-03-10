package com.example.question_service.question.entity;

import com.example.question_service.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "hash_tag")
public class HashTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hashTag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionHashTag> questionHashTags = new ArrayList<>();

    public void updateQuestionHashTag(QuestionHashTag questionHashTag) {
        this.questionHashTags.add(questionHashTag);
        questionHashTag.updateHashTag(this);
    }
}
