package ru.hse.elarateam.orders;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import ru.hse.elarateam.orders.web.services.jwt.ServiceTokenUtilsImpl;

@Slf4j
@EnableSpringDataWebSupport
@EnableFeignClients
@SpringBootApplication
public class ElaraOrdersApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ElaraOrdersApplication.class, args);
    }

}
