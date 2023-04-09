package ru.hse.elarateam.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ElaraUsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraUsersApplication.class, args);
    }
}
