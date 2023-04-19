package ru.hse.elarateam.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ElaraLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraLoginApplication.class, args);
    }

//    @Bean
//    @Transactional
//    public CommandLineRunner initDatabase(JWTUtilsImpl JWTUtils, ServiceTokenUtilImpl serviceTokenUtil) {
//        return args -> {
////            var token = JWTUtils.generateToken("admin@gmail.com");
////            log.info("admin token: " + token);
////            log.info("service token authorized: " + serviceTokenUtil.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2ZGI2ZTg2YS0wMGUwLTQ2MGYtODVmYi0zNzZjMjcyZTJmMDgiLCJpc3MiOiJlbGFyYS1sb2dpbiIsImF1ZCI6ImVsYXJhLWxvZ2luIiwiaWF0IjoxNjgwOTY0NTQ4fQ.KWBxGyiQxs-Ypd98qZJPU3TEb_nnnhkR55hVxQFumzsOJQVtTMLdjtxUJI59cg5tP2ptpCrc-TmaeOyOulzLSA"));
////            log.info(JWTUtils.getUserLoginFromToken("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlbGFyYS1sb2dpbiIsImF1ZCI6ImVsYXJhIiwiaWF0IjoxNjgxNzU5NDg4LCJzdWIiOiJhZG1pbkBnbWFpbC5jb20ifQ.UKlDVn-prdw7RDQDwFg0DwaBW2OKU9eJtphsoBRrqZM1EaEYPqb_5YVxBAXoyPYuEcTdlzoUfIYH2kHqa3Uw5Q"));
//        };
//    }
}
