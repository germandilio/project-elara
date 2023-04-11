package ru.hse.elarateam.users.web.controllers.advice;

public class UserNotFountException extends RuntimeException {
    public UserNotFountException(String message) {
        super(message);
    }

    public UserNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFountException(Throwable cause) {
        super(cause);
    }
}
