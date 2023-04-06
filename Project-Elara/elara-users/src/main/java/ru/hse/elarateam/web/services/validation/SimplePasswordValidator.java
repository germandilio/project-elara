package ru.hse.elarateam.web.services.validation;

import org.springframework.beans.factory.annotation.Value;

public class SimplePasswordValidator implements PasswordValidator {
    @Value("${elara.validation.password.min-length}")
    private int minPasswordLength;

    @Value("${elara.validation.password.max-length}")
    private int maxPasswordLength;

    @Override
    public boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= 6 && password.length() <= 80 && !password.isBlank();
    }
}
