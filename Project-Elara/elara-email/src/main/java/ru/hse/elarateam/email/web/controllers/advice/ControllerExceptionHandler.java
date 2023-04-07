package ru.hse.elarateam.email.web.controllers.advice;

import com.postmarkapp.postmark.client.exception.PostmarkException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(PostmarkException.class)
    public ResponseEntity<String> handlePostmarkException(PostmarkException e) {
        log.warn("Postmark exception: {}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handlePostmarkException(IOException e) {
        log.warn("Mailing provider is unavailable: {}", e.getMessage());
        return new ResponseEntity<>("Mailing provider is unavailable", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handlePostmarkException(Exception e) {
        log.warn("Internal server error: {}", e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
