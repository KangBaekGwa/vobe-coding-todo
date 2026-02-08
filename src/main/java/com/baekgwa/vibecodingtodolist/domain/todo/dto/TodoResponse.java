package com.baekgwa.vibecodingtodolist.domain.todo.dto;

import com.baekgwa.vibecodingtodolist.model.todo.entity.TodoEntity;
import java.time.LocalDateTime;

public record TodoResponse(
    Long id,
    String content,
    boolean isCompleted,
    LocalDateTime createdAt
) {
    public static TodoResponse from(TodoEntity entity) {
        return new TodoResponse(
            entity.getId(),
            entity.getContent(),
            entity.getIsCompleted(),
            entity.getCreatedAt()
        );
    }
}
