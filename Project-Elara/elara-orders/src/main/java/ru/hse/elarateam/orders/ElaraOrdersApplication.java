package ru.hse.elarateam.orders;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@Slf4j
@EnableSpringDataWebSupport
@EnableFeignClients
@SpringBootApplication
public class ElaraOrdersApplication {

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ElaraOrdersApplication.class, args);
    }

}
