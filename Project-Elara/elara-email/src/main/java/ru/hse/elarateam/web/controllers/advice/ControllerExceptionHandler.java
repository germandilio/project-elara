package ru.hse.elarateam.web.controllers.advice;

import com.postmarkapp.postmark.client.exception.PostmarkException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(PostmarkException.class)
    public ResponseEntity<String> handlePostmarkException(PostmarkException e) {
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handlePostmarkException(IOException e) {
        return new ResponseEntity<>("Mailing provider is unavailable", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handlePostmarkException(Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
