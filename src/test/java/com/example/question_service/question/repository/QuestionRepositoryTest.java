package com.example.question_service.question.repository;

import com.example.question_service.config.TestConfig;
import com.example.question_service.question.entity.Answer;
import com.example.question_service.question.entity.Question;
import com.example.question_service.question.entity.Status;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void init() {

        //init data
    }

    @Test
    @DisplayName("질문 등록 테스트")
    public void createQuestionTest() {

        //given
        Question question = Question.of("질문 제목 입니다.", "질문은 무엇인가요?", "작성자", Status.ING);

        //when
        Question save = questionRepository.save(question);

        //then
        em.flush();
        em.clear();

        assertEquals(question.getSubject(), save.getSubject());
    }

    @Test
    @DisplayName("답변 등록 테스트")
    public void createAnswerTest() {

        //given
        Question question = Question.of("질문 제목 입니다.", "질문은 무엇인가요?", "작성자", Status.ING);
        Answer answer = Answer.createAnswer("답변입니다.", "작성자", question);
        question.updateAnswer(answer);

        //when
        Question save = questionRepository.save(question);

        //then
        em.flush();
        em.clear();

        assertEquals(question.getAnswers().size(), 1);
    }

    @Test
    @DisplayName("다수 질문 추가 테스트")
    public void createManyAnswerTest() {

        //given
        Question question = Question.of("질문 제목 입니다.", "질문은 무엇인가요?", "작성자", Status.ING);
        for (int i=0;i<10;i++) {
            Answer answer = Answer.createAnswer("답변입니다." + i, "작성자" + i, question);
            question.updateAnswer(answer);
        }

        //when
        questionRepository.save(question);

        //then
        em.flush();
        em.clear();

        //test N+1
        question.getAnswers().forEach(answer -> log.info("answer : {}", answer.getContent()));

        assertEquals(question.getAnswers().size(), 10);
    }

    @Test
    @DisplayName("질문 수정 테스트")
    public void updateAnswerTest() {

        //given

        //when

        //then
    }
}