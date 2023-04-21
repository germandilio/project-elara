package ru.hse.elarateam.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@EnableFeignClients
@SpringBootApplication
// здесь могла быть ваша интеграция с логистической компанией
public class ElaraDeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraDeliveryApplication.class, args);
    }
}
