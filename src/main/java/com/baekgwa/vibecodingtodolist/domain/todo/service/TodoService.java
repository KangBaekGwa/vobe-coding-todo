package com.baekgwa.vibecodingtodolist.domain.todo.service;

import com.baekgwa.vibecodingtodolist.domain.todo.dto.TodoResponse;
import com.baekgwa.vibecodingtodolist.model.todo.entity.TodoEntity;
import com.baekgwa.vibecodingtodolist.model.todo.repository.TodoRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoService {

    private final TodoRepository todoRepository;

    public List<TodoResponse> getTodos() {
        return todoRepository.findAllByOrderByCreatedAtDesc().stream()
            .map(TodoResponse::from)
            .collect(Collectors.toList());
    }

    public List<TodoResponse> getTodayTodos() {
        return todoRepository.findAllByDeadline(LocalDate.now()).stream()
            .map(TodoResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public void addTodo(String content, LocalDate deadline) {
        todoRepository.save(TodoEntity.of(content, deadline));
    }

    @Transactional
    public void toggleTodo(Long id) {
        TodoEntity todo = todoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        todo.toggleCompletion();
    }

    @Transactional
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
}
