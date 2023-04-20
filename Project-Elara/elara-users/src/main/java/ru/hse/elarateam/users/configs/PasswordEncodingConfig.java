package ru.hse.elarateam.users.configs;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Setter
@Configuration
public class PasswordEncodingConfig {
    @Value("${elara.users.password-encoding.salt}")
    private String salt;

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        // return new BCryptPasswordEncoder(12, new SecureRandom(salt.getBytes()));
        return NoOpPasswordEncoder.getInstance();
    }
}
