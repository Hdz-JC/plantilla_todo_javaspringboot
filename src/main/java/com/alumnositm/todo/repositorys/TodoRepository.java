package com.alumnositm.todo.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alumnositm.todo.entities.TodoEntity;
import java.util.List;
import java.util.Optional;


public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    
    List<TodoEntity> findByDeletedFalse(); // Trae solo los activos

    Optional<TodoEntity> findByIdAndDeletedFalse(Long id); 

    
}
