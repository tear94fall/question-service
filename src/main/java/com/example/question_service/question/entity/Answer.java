package com.example.question_service.question.entity;

import com.example.question_service.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(of = {"id", "content", "author"})
@Table(name = "answer")
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public void updateQuestion(Question question) {
        this.question = question;
    }

    public void updateComment(Comment comment) {
        this.comments.add(comment);
        comment.updateAnswer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Answer)) return false;
        return id != null && id.equals(((Answer) o).getId());
    }

    @Override
    public int hashCode() {
        return id != null ? id.intValue() : 0;
    }

    @Builder
    public Answer(String content, String author, Question question) {
        this.content = content;
        this.author = author;
        this.question = question;
    }

    public static Answer createAnswer(String content, String author, Question question) {
        return Answer.builder()
                .content(content)
                .author(author)
                .question(question)
                .build();
    }
}
