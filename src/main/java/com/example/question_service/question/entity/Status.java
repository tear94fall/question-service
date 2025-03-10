package com.example.question_service.question.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Status {

    NEW("new"),
    ING("ing"),
    DONE("done");

    private final String status;

    public static Status fromString(String status) {
        return Arrays.stream(Status.values())
                .filter(s -> s.getStatus().equals(status))
                .findAny().orElse(null);
    }
}
