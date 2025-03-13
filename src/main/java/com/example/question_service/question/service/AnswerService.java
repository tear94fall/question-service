package com.example.question_service.question.service;

import com.example.question_service.question.dto.AnswerDto;
import com.example.question_service.question.entity.Answer;
import com.example.question_service.question.entity.AnswerStatus;
import com.example.question_service.question.entity.Question;
import com.example.question_service.question.entity.QuestionStatus;
import com.example.question_service.question.repository.AnswerRepository;
import com.example.question_service.question.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public List<AnswerDto> getAnswersByQuestionId(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));
        
        return question.getAnswers().stream()
                .map(AnswerDto::from)
                .collect(Collectors.toList());
    }

    public AnswerDto getAnswer(Long answerId) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found with id: " + answerId));
        
        return AnswerDto.from(answer);
    }

    @Transactional
    public AnswerDto createAnswer(Long questionId, AnswerDto answerDto) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));
        
        Answer answer = Answer.builder()
                .content(answerDto.getContent())
                .author(answerDto.getAuthor())
                .status(AnswerStatus.NOT_ACCEPTED)
                .build();
        
        question.updateAnswer(answer);
        answerRepository.save(answer);
        
        return AnswerDto.from(answer);
    }

    @Transactional
    public AnswerDto updateAnswer(Long answerId, AnswerDto answerDto) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found with id: " + answerId));
        
        // 내용만 업데이트 가능
        if (answerDto.getContent() != null && !answerDto.getContent().isEmpty()) {
            answer.updateContent(answerDto.getContent());
        }
        
        return AnswerDto.from(answer);
    }

    @Transactional
    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
    }
    
    @Transactional
    public AnswerDto acceptAnswer(Long questionId, Long answerId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new EntityNotFoundException("Question not found with id: " + questionId));
        
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new EntityNotFoundException("Answer not found with id: " + answerId));
        
        // 해당 질문에 속한 답변인지 확인
        if (!answer.getQuestion().getId().equals(questionId)) {
            throw new IllegalArgumentException("Answer does not belong to the specified question");
        }
        
        // 이미 채택된 답변이 있는지 확인하고 모두 NOT_ACCEPTED로 변경
        question.getAnswers().forEach(a -> a.updateStatus(AnswerStatus.NOT_ACCEPTED));
        
        // 선택된 답변 채택
        answer.updateStatus(AnswerStatus.ACCEPTED);
        
        // 질문 상태 변경
        question.updateStatus(QuestionStatus.DONE);
        
        return AnswerDto.from(answer);
    }
} 