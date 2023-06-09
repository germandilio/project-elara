package ru.hse.elarateam.users.web.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Converts password to its hash using provided {@link PasswordEncoder}.
 */
@Slf4j
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
        // return Hashing.sha256().hashString(attribute, StandardCharsets.UTF_8).toString();
        var encoded = passwordEncoder.encode(attribute);
        log.trace("CONVERTING PASSWORD: {}, to hash: {}", attribute, encoded);
        return encoded;
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

//    public boolean matches(String rawPassword, String passwordDB) {
//        final var hashPresentPassword = Hashing.sha256().hashString(rawPassword, StandardCharsets.UTF_8).toString();
//        log.debug("Presented password hash: {}, get from db: {}", hashPresentPassword, passwordDB);
//        return Objects.equals(hashPresentPassword, passwordDB);
//    }
}
