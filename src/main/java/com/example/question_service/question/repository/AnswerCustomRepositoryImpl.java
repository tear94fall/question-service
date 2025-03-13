package com.example.question_service.question.repository;

import com.example.question_service.question.entity.Answer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.example.question_service.question.entity.QAnswer.answer;

@RequiredArgsConstructor
public class AnswerCustomRepositoryImpl implements AnswerCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Answer> findAnswersByQuestionId(Long questionId) {
        return queryFactory
                .selectFrom(answer)
                .where(answer.question.id.eq(questionId))
                .fetch();
    }

    @Override
    public List<Answer> findAnswersByAuthor(String author) {
        return queryFactory
                .selectFrom(answer)
                .where(answer.author.eq(author))
                .fetch();
    }
} 