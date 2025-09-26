package com.alumnositm.todo.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTodoRequest {
    private String title;
    private String description;
    private boolean deleted;

    public CreateTodoRequest( String title, String description, boolean deleted) {
        this.title = title;
        this.description = description;
        this.deleted = deleted;
    }
}
