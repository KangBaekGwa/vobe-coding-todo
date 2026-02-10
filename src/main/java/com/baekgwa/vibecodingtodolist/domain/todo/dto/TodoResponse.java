package com.baekgwa.vibecodingtodolist.domain.todo.dto;

import com.baekgwa.vibecodingtodolist.model.todo.entity.TodoEntity;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TodoResponse(
    Long id,
    String content,
    boolean isCompleted,
    LocalDate deadline,
    LocalDateTime createdAt
) {
    public static TodoResponse from(TodoEntity entity) {
        return new TodoResponse(
            entity.getId(),
            entity.getContent(),
            entity.getIsCompleted(),
            entity.getDeadline(),
            entity.getCreatedAt()
        );
    }
}
