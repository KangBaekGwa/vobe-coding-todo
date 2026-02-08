package com.baekgwa.vibecodingtodolist.model.todo.repository;

import com.baekgwa.vibecodingtodolist.model.todo.entity.TodoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findAllByOrderByCreatedAtDesc();
}
