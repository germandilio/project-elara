package ru.hse.elarateam.adminconsole;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ElaraAdminConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraAdminConsoleApplication.class, args);
    }
}
