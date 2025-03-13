package com.example.question_service.question.repository;

import com.example.question_service.question.entity.Answer;

import java.util.List;

public interface AnswerCustomRepository {
    List<Answer> findAnswersByQuestionId(Long questionId);
    List<Answer> findAnswersByAuthor(String author);
} 