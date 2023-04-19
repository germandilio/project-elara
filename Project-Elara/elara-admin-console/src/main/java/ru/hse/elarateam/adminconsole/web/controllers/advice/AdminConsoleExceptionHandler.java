package ru.hse.elarateam.adminconsole.web.controllers.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hse.elarateam.adminconsole.web.controllers.AdminConsoleController;

@ControllerAdvice(assignableTypes = {AdminConsoleController.class})
public class AdminConsoleExceptionHandler {
    //todo доделать
    //todo запилить кастомные исключения
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
