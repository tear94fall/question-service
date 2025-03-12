package com.example.question_service.question.service;

import com.example.question_service.question.dto.QuestionDto;
import com.example.question_service.question.entity.Question;
import com.example.question_service.question.entity.QuestionStatus;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;


    @BeforeEach
    public void init() {

        //init data
        Question question = Question.of("궁금해요", "이것은 무엇인가요?", "작성자", QuestionStatus.ING);

        questionService.createQuestion(QuestionDto.from(question));
    }

    @Test
    @DisplayName("질문 등록 테스트")
    public void createQuestionTest() {

        //given
        Question question = Question.of("질문 제목 입니다.", "질문은 무엇인가요?", "작성자", QuestionStatus.ING);
        QuestionDto saveQuestion = questionService.createQuestion(QuestionDto.from(question));

        //when
        QuestionDto findQuestion = questionService.getQuestion(saveQuestion.getId());

        //then
        assertEquals(saveQuestion.getId(), findQuestion.getId());
    }

    @Test
    @DisplayName("질문 수정 테스트")
    public void updateQuestionTest() {

        //given

        //when

        //then
    }
}