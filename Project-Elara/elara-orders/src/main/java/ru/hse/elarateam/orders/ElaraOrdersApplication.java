package ru.hse.elarateam.orders;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@SpringBootApplication
public class ElaraOrdersApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ElaraOrdersApplication.class, args);
    }
}
