package ru.hse.elarateam.users.web.controllers.advice;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    // TODO add more precise exception handlers
    // TODO pretify error messages for validation

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.warn("Exception: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Required parameter '" + name + "' is missing");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseBody
    public ResponseEntity<Object> handleMissingHeaders(MissingRequestHeaderException ex) {
        String name = ex.getHeaderName();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Required header '" + name + "' is missing");
    }

    @ExceptionHandler(UserNotFountException.class)
    public ResponseEntity<?> handleException(UserNotFountException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleException(ConflictException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleException(ConstraintViolationException e) {
        log.warn("Constraint violation exception : {}", e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleException(ValidationException e) {
        log.info("Validation exception: {}", e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(DataIntegrityViolationException e) {
        log.warn("Data integrity exception : {}", e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleException(IllegalArgumentException e) {
        log.info("Send response with Illegal argument exception: {}", e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleException(IllegalStateException e) {
        log.error("Illegal state exception: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    // TODO custom fallback with email service and login service
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<?> handleException(FeignException e) {
        log.error("Feign exception: {}", e.getMessage(), e);
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

}
