package com.nutribite.ai.exception;
import org.springframework.dao.DataIntegrityViolationException; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import java.time.LocalDateTime; import java.util.*;
@RestControllerAdvice public class GlobalExceptionHandler {
 private ResponseEntity<Map<String,Object>> body(HttpStatus status,String message){Map<String,Object>b=new LinkedHashMap<>();b.put("timestamp",LocalDateTime.now());b.put("status",status.value());b.put("error",status.getReasonPhrase());b.put("message",message);return ResponseEntity.status(status).body(b);}
 @ExceptionHandler(FoodNotFoundException.class) public ResponseEntity<Map<String,Object>> food(FoodNotFoundException e){return body(HttpStatus.NOT_FOUND,e.getMessage());}
 @ExceptionHandler(NoSuchElementException.class) public ResponseEntity<Map<String,Object>> missing(NoSuchElementException e){return body(HttpStatus.NOT_FOUND,e.getMessage()==null?"Resource not found":e.getMessage());}
 @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<Map<String,Object>> illegal(IllegalArgumentException e){return body(HttpStatus.BAD_REQUEST,e.getMessage());}
 @ExceptionHandler(DataIntegrityViolationException.class) public ResponseEntity<Map<String,Object>> conflict(DataIntegrityViolationException e){return body(HttpStatus.CONFLICT,"The request conflicts with existing data.");}
 @ExceptionHandler(MethodArgumentNotValidException.class) public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException e){Map<String,String> errors=new LinkedHashMap<>();e.getBindingResult().getFieldErrors().forEach(x->errors.put(x.getField(),x.getDefaultMessage()));Map<String,Object>b=new LinkedHashMap<>();b.put("timestamp",LocalDateTime.now());b.put("status",400);b.put("error","Bad Request");b.put("message","Validation failed");b.put("fields",errors);return ResponseEntity.badRequest().body(b);}
 @ExceptionHandler(Exception.class) public ResponseEntity<Map<String,Object>> generic(Exception e){return body(HttpStatus.INTERNAL_SERVER_ERROR,"An unexpected error occurred.");}
}
