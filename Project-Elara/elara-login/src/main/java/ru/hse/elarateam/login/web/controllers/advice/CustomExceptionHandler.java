package ru.hse.elarateam.login.web.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<String> handleIllegalArgumentException(UnauthorizedException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
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
}
