package ru.hse.elarateam.login.web.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Converts password to its hash using provided {@link PasswordEncoder}.
 */
@Getter
@Component
@Converter
public class PasswordConverter implements AttributeConverter<String, String> {
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Converts password to its hash using provided {@link PasswordEncoder}.
     *
     * @param attribute the entity attribute value to be converted
     * @return the database column value to which the attribute value should be converted
     */
    @Override
    public String convertToDatabaseColumn(String attribute) {
        return passwordEncoder.encode(attribute);
    }

    /**
     * Converts password hash to its original value.
     *
     * @param dbData the database column value to be converted
     * @return the entity attribute value to which the database column value should be converted
     */
    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
