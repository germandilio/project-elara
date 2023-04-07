package ru.hse.elarateam.users.web.services.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimplePasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Value("${elara.validation.password.min-length}")
    private int minPasswordLength;

    @Value("${elara.validation.password.max-length}")
    private int maxPasswordLength;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null) {
            return false;
        }
        return password.length() >= 6 && password.length() <= 80 && !password.isBlank();

    }
}
