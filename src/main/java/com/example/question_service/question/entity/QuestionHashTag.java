package com.example.question_service.question.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionHashTag {

    @Id
    @Column(name = "question_hash_tag_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hash_tag_id")
    private HashTag hashTag;

    public QuestionHashTag(Question question, HashTag hashTag) {
        this.question = question;
        this.hashTag = hashTag;
        question.updateQuestionHashTag(this);
        hashTag.updateQuestionHashTag(this);
    }

    public void updateQuestion(Question question) {
        this.question = question;
    }

    public void updateHashTag(HashTag hashTag) {
        this.hashTag = hashTag;
    }
}
