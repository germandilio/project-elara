package ru.hse.elarateam.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElaraLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraLoginApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner createServiceToken(ServiceTokenUtils utils) {
//        return (args) -> System.out.println("utils.generateToken() = " + utils.generateToken());
//    }
}
