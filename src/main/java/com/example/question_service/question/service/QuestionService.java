package com.example.question_service.question.service;

import com.example.question_service.question.dto.QuestionDto;
import com.example.question_service.question.entity.Question;
import com.example.question_service.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionDto getQuestion(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return QuestionDto.from(question);
    }

    @Transactional
    public QuestionDto createQuestion(QuestionDto questionDto) {
        Question saveQuestion = questionRepository.save(Question.of(questionDto));
        return QuestionDto.from(saveQuestion);
    }

    @Transactional
    public QuestionDto updateQuestion(Long id, QuestionDto questionDto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        question.updateContent(questionDto.getContent().isEmpty() ? question.getContent() : questionDto.getContent());

        return QuestionDto.from(question);
    }

    @Transactional
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }
}
