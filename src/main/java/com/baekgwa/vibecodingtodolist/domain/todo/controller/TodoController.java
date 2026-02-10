package com.baekgwa.vibecodingtodolist.domain.todo.controller;

import com.baekgwa.vibecodingtodolist.domain.todo.service.TodoService;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/")
    public String getTodos(Model model) {
        model.addAttribute("todos", todoService.getTodos());
        model.addAttribute("todayTodos", todoService.getTodayTodos());
        return "todo/list";
    }

    @PostMapping("/todos")
    public String addTodo(
        @RequestParam String content,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deadline
    ) {
        todoService.addTodo(content, deadline);
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/toggle")
    public String toggleTodo(@PathVariable Long id) {
        todoService.toggleTodo(id);
        return "redirect:/";
    }

    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/";
    }
}
