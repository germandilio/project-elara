package ru.hse.elarateam.users.web.controllers.advice;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    // TODO add more precise exception handlers
    // TODO pretify error messages for validation

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.warn("Exception: {}", e.getMessage());
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleException(ConstraintViolationException e) {
        log.warn("Constraint violation exception : {}", e.getMessage());
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleException(IllegalArgumentException e) {
        log.info("Send response with Illegal argument exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleException(IllegalStateException e) {
        log.warn("Illegal state exception: {}", e.getMessage());
        e.printStackTrace();
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleException(FeignException e) {
        log.warn("Feign exception: {}", e.getMessage());
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleException(ValidationException e) {
        log.info("Validation exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
