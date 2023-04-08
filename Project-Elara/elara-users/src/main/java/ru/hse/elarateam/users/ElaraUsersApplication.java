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

//    @Bean
//    public CommandLineRunner createServiceToken(ServiceTokenUtils utils) {
//        return (args) -> System.out.println("utils.generateToken() = " + utils.generateToken());
//    }

//    @Bean
//    public CommandLineRunner initDatabase(UsersDBService usersDBService) {
//        return (args) -> {
//            var userDTO = UserRegisterRequestDTO.builder()
//                    .login("ddtatyanchenko@edu.hse.ru")
//                    .password("12345678")
//                    .firstName("Dmitry")
//                    .lastName("Tatyanchenko")
//                    .build();
//
//            usersDBService.saveUser(userDTO);
//
//            userDTO = UserRegisterRequestDTO.builder()
//                    .login("glmikhaylov@edu.hse.ru")
//                    .password("111111")
//                    .firstName("German")
//                    .lastName("Mikhaylov")
//                    .build();
//
//            usersDBService.saveUser(userDTO);
//        };
//    }
}
