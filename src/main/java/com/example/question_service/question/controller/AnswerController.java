package com.example.question_service.question.controller;

import com.example.question_service.question.dto.AnswerDto;
import com.example.question_service.question.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/questions/{questionId}/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    @GetMapping
    public ResponseEntity<List<AnswerDto>> getAnswers(@PathVariable("questionId") Long questionId) {
        return ResponseEntity.ok(answerService.getAnswersByQuestionId(questionId));
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDto> getAnswer(
            @PathVariable("questionId") Long questionId,
            @PathVariable("answerId") Long answerId) {
        return ResponseEntity.ok(answerService.getAnswer(answerId));
    }

    @PostMapping
    public ResponseEntity<AnswerDto> createAnswer(
            @PathVariable("questionId") Long questionId,
            @RequestBody AnswerDto answerDto) {
        return ResponseEntity.ok(answerService.createAnswer(questionId, answerDto));
    }

    @PutMapping("/{answerId}")
    public ResponseEntity<AnswerDto> updateAnswer(
            @PathVariable("questionId") Long questionId,
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerDto answerDto) {
        return ResponseEntity.ok(answerService.updateAnswer(answerId, answerDto));
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity<Void> deleteAnswer(
            @PathVariable("questionId") Long questionId,
            @PathVariable("answerId") Long answerId) {
        answerService.deleteAnswer(answerId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{answerId}/accept")
    public ResponseEntity<AnswerDto> acceptAnswer(
            @PathVariable("questionId") Long questionId,
            @PathVariable("answerId") Long answerId) {
        return ResponseEntity.ok(answerService.acceptAnswer(questionId, answerId));
    }
} 