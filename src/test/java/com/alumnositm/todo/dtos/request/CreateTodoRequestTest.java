package com.alumnositm.todo.dtos.request;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
public class CreateTodoRequestTest {

    @Test
    void testConstructorAndGetterSetter() {
        String title = " prueba de todo";
        String descripcion = "pruebas de cuerpo de la descripcion";
        CreateTodoRequest createTodoRequest = new CreateTodoRequest(title, descripcion);
        assertEquals(descripcion, createTodoRequest.getDescription());
        assertEquals(title, createTodoRequest.getTitle());

    }

    // *profe quiere que aga una instania con valores en ella
    // *el profe quere que modifique los valores
    // *el profe quiere que compare los valores modificados con la instancia
    @Test
    void testConstructorSetterModify() {
        String title = " prueba de todo";
        String descripcion = null;
        String titleModify = " prueba de todo or que dice lo mismo mi compa";
        String descripcionModify = "pruebas de cuerpo de la descripcion es que nose que poner el profe me dejo esto";

        CreateTodoRequest createTodoRequest = new CreateTodoRequest(title, descripcion);
        
        createTodoRequest.setTitle(titleModify);
        createTodoRequest.setDescription(descripcionModify);

        assertEquals(titleModify, createTodoRequest.getTitle());
        assertEquals(descripcionModify, createTodoRequest.getDescription());

        assertNotEquals(title, createTodoRequest.getTitle());
        assertNotEquals(descripcion, createTodoRequest.getDescription());

    }

    @Test
    void testTitleNotblankValidation(){
        Set<ConstraintViolation<CreateTodoRequest>> violations;
        Validator validator= Validation.buildDefaultValidatorFactory().getValidator();

        //Test null title
        CreateTodoRequest requestNullTitleTodo=new CreateTodoRequest(null,"descripcion valida");
        violations=validator.validate(requestNullTitleTodo);
        assertTrue(violations.stream().anyMatch(v->v.getMessage().equals("El titulo no puede estar vacio")));

         //Test empty title
        CreateTodoRequest requestEmptyTitleTodo=new CreateTodoRequest("","descripcion valida");
        violations=validator.validate(requestEmptyTitleTodo);
        assertTrue(violations.stream().anyMatch(v->v.getMessage().equals("El titulo no puede estar vacio")));

        //Test space title
        CreateTodoRequest requestSpaceTitleTodo=new CreateTodoRequest("     ","descripcion valida");
        violations=validator.validate(requestSpaceTitleTodo);
        assertTrue(violations.stream().anyMatch(v->v.getMessage().equals("El titulo no puede estar vacio")));
        
       
    }

    @Test
    void testDescriptionNotBlankValidation(){
        Set<ConstraintViolation<CreateTodoRequest>> violations;
        Validator validator= Validation.buildDefaultValidatorFactory().getValidator();

        //Prueba de descripcion nula
        CreateTodoRequest requestNullDescriptionTodo = new CreateTodoRequest("Titulo valido", null);
        violations= validator.validate(requestNullDescriptionTodo);
        assertTrue(violations.stream().anyMatch(d->d.getMessage().equals("La descripcion no puede estar vacia")));

        //Prueba de descripcion Vacia
        CreateTodoRequest requestEmptyDescriptionTodo = new CreateTodoRequest("Titulo valido", "");
        violations=validator.validate(requestEmptyDescriptionTodo);
        assertTrue(violations.stream().anyMatch(d->d.getMessage().equals("La descripcion no puede estar vacia")));

        //Prueba descripcion con espacios en blanco
        CreateTodoRequest requestBlankSpaceDescriptionTodo = new CreateTodoRequest("Titulo valido", "    ");
        violations= validator.validate(requestBlankSpaceDescriptionTodo);
        assertTrue(violations.stream().anyMatch(d->d.getMessage().equals("La descripcion no puede estar vacia")));
    }

    @Test
    void testRegex(){
         //Test Expresion regular
        CreateTodoRequest requestRegexValidateTitle = new CreateTodoRequest("Titulovalido", "descripcion valida");
        assertTrue(requestRegexValidateTitle.getTitle().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\\\s.!]+$"));

        CreateTodoRequest requestRegexCaracteres = new CreateTodoRequest("$#$%#$", "descripcion valida");
        assertFalse(requestRegexCaracteres.getTitle().matches("^[A-Za-zÁÉÍÓÚáéíóúÑñ\\\\s.!]+$"));
    }

    @Test
    void testRegularExpressions(){
        Set<ConstraintViolation<CreateTodoRequest>> violations;
        Validator validator= Validation.buildDefaultValidatorFactory().getValidator();
        
        CreateTodoRequest requestTestRegularExpressionTitle = new CreateTodoRequest("Titulo valido 123", "descripcion valida");
        violations = validator.validate(requestTestRegularExpressionTitle);
        System.out.println(violations.isEmpty()? "cumplimos con el regex":"No cumpimos con el regex");
        assertTrue(violations.stream().anyMatch(v->v.getMessage().equals("El titulo no puede tener espacios al inicio o al final")));
    }
}
