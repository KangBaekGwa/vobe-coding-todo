package com.baekgwa.vibecodingtodolist.model.todo.entity;

import com.baekgwa.vibecodingtodolist.model.TemporalEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "todos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TodoEntity extends TemporalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isCompleted;

    @Column(name = "deadline")
    private LocalDate deadline;

    private TodoEntity(String content, LocalDate deadline) {
        this.content = content;
        this.isCompleted = false;
        this.deadline = deadline;
    }

    public static TodoEntity of(String content, LocalDate deadline) {
        return new TodoEntity(content, deadline);
    }

    public static TodoEntity of(String content) {
        return new TodoEntity(content, null);
    }

    public void toggleCompletion() {
        this.isCompleted = !this.isCompleted;
    }

    public void changeDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public boolean isDue(LocalDate date) {
        return deadline != null && deadline.isEqual(date);
    }
}
