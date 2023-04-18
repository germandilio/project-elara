package ru.hse.elarateam.orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.transaction.annotation.Transactional;
import ru.hse.elarateam.orders.services.jwt.ServiceTokenUtilsImpl;

@Slf4j
@EnableSpringDataWebSupport
@EnableFeignClients
@SpringBootApplication
public class ElaraOrdersApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ElaraOrdersApplication.class, args);
    }

    @Bean
    @Transactional
    public CommandLineRunner initDatabase(ServiceTokenUtilsImpl serviceTokenUtil) {
        return args -> {
//            var token = serviceTokenUtil.generateToken();
//            log.info("service token: " + token);
//            log.info("service token authorized: " + serviceTokenUtil.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlbGFyYS1vcmRlcnMiLCJhdWQiOiJlbGFyYSIsImlhdCI6MTY4MTgxODYzNX0.YFeHn3NnjzIo2DsvteP9tiMyJFrGK054WV01pHyfp1d01KAwOFY6f-AMA6M3N17Tj632Li0gR-YBZOjDc0LmyA"));
//            log.info(JWTUtils.getUserLoginFromToken("eyJhbGciOiJIUzUxMiJ9.eyJpc3MiOiJlbGFyYS1sb2dpbiIsImF1ZCI6ImVsYXJhIiwiaWF0IjoxNjgxNzU5NDg4LCJzdWIiOiJhZG1pbkBnbWFpbC5jb20ifQ.UKlDVn-prdw7RDQDwFg0DwaBW2OKU9eJtphsoBRrqZM1EaEYPqb_5YVxBAXoyPYuEcTdlzoUfIYH2kHqa3Uw5Q"));
        };
    }
}
