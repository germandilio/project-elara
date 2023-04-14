package ru.hse.elarateam.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class ElaraEurekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraEurekaApplication.class, args);
    }
}
