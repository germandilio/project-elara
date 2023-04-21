package ru.hse.elarateam.orders.web.controllers.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hse.elarateam.orders.web.controllers.OrdersController;

@Slf4j
@ControllerAdvice(assignableTypes = {OrdersController.class})
public class OrdersExceptionHandler {
    //todo доделать
    //todo запилить кастомные исключения
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        log.error(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
