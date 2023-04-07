package ru.hse.elarateam.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ru.hse.elarateam.users.dto.requests.UserRegisterRequestDTO;
import ru.hse.elarateam.users.web.services.UsersDBService;
import ru.hse.elarateam.users.web.services.tokens.ServiceTokenUtils;

@EnableFeignClients
@SpringBootApplication
public class ElaraUsersApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraUsersApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner createServiceToken(ServiceTokenUtils utils) {
//        return (args) -> System.out.println("utils.generateToken() = " + utils.generateToken());
//    }

    @Bean
    public CommandLineRunner initDatabase(UsersDBService usersDBService) {
        return (args) -> {
//            var userDTO = UserRegisterRequestDTO.builder()
//                    .login("ddtatyanchenko@edu.hse.ru")
//                    .password("12345678")
//                    .firstName("Dmitry")
//                    .lastName("Tatyanchenko")
//                    .build();
//
//            usersDBService.saveUser(userDTO);

//            var userDTO = UserRegisterRequestDTO.builder()
//                    .login("glmikhaylov@edu.hse.ru")
//                    .password("111111")
//                    .firstName("German")
//                    .lastName("Mikhaylov")
//                    .build();
//
//            usersDBService.saveUser(userDTO);
        };
    }
}
