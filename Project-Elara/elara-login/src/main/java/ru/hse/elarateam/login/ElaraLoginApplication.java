package ru.hse.elarateam.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.hse.elarateam.login.web.services.jwt.JWTUtilsImpl;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class ElaraLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraLoginApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner initDatabase(JWTUtilsImpl JWTUtils) {
//        return args -> {
//            var token = JWTUtils.generateToken("lemon-doge-admin-login");
//            log.info("admin token: " + token);
//        };
//    }
}
