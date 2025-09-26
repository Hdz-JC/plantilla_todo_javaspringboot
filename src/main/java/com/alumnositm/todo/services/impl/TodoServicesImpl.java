package com.alumnositm.todo.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alumnositm.todo.dtos.request.CreateTodoRequest;
import com.alumnositm.todo.entities.TodoEntity;
import com.alumnositm.todo.helpers.TodoStatus;
import com.alumnositm.todo.repositorys.TodoRepository;
import com.alumnositm.todo.services.TodoServices;

@Service
public class TodoServicesImpl implements TodoServices {

    private final TodoRepository todoRepository;

    public TodoServicesImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoEntity> allTodos() {
        // List<TodoEntity> todos = List.of(
        //     new TodoEntity(1L, "Learn Spring Boot", "Complete the Spring Boot tutorial", TodoStatus.PENDING),
        //     new TodoEntity(2L, "Build a REST API", "Create a RESTful API using Spring Boot", TodoStatus.IN_PROGRESS),
        //     new TodoEntity(3L, "Write Unit Tests", "Write unit tests for the application", TodoStatus.COMPLETED)
        // );
       //List<TodoEntity> todos  = todoRepository.findAll();
        return todoRepository.findByDeletedFalse();
    }

    @Override
    public TodoEntity createTodo(CreateTodoRequest createTodoRequest) {
    //    TodoEntity entity= TodoEntity.builder()
    //    .title(createTodoRequest.getTitle())
    //    .description(createTodoRequest.getDescription())
    //    .status(TodoStatus.PENDING)
    //    .build();

    TodoEntity entity = new TodoEntity();
    entity.setTitle(createTodoRequest.getTitle());
    entity.setDescription(createTodoRequest.getDescription());
    entity.setStatus(TodoStatus.PENDING);
    entity.setDeleted(false);

       return todoRepository.save(entity);
    }

    @Override
    public TodoEntity findById(int idTodo) {
      // TodoEntity todo = todoRepository.findById((long)idTodo).orElse(null);
       return todoRepository.findByIdAndDeletedFalse(Long.valueOf(idTodo))
                .orElse(null);
    }

    @Override
    public TodoEntity updateTodoById(int idTodo, CreateTodoRequest entity) {
        TodoEntity todoEntity = todoRepository.findById((long)idTodo).orElse(null);
        if(todoEntity!=null){
            todoEntity.setTitle(entity.getTitle());
            todoEntity.setDescription(entity.getDescription());
            todoEntity.setStatus(TodoStatus.COMPLETED);
            todoEntity.setDeleted(entity.isDeleted());
            todoRepository.save(todoEntity);
            return todoEntity;
        }
        return null;

    }

     @Override
    public TodoEntity deleteTodoById(Long idTodo) {
        // First find the todo (only non-deleted ones)
        return todoRepository.findByIdAndDeletedFalse(idTodo)
            .map(todo -> {
                todoRepository.deleteById(idTodo);
                return todo; // return the entity before deletion
            })
            .orElseThrow(() -> new RuntimeException("Todo con ID " + idTodo + " no encontrado"));
    }

   @Override
    public TodoEntity softDeleteById(int idTodo,CreateTodoRequest entity) {
        TodoEntity todoEntity = todoRepository.findById((long)idTodo).orElse(null);
        if(todoEntity!=null){
            todoEntity.setTitle(entity.getTitle());
            todoEntity.setDescription(entity.getDescription());
            todoEntity.setStatus(TodoStatus.COMPLETED);
            todoEntity.setDeleted(true);
            todoRepository.save(todoEntity);
            return todoEntity;
        }
        return null;
    }

}


    




