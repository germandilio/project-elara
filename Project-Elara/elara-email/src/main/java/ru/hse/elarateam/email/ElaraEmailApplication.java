package ru.hse.elarateam.email;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ElaraEmailApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraEmailApplication.class, args);
    }
}
