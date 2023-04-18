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
    //todo !!! проинициализировать базу данных адресом отправки и положить его id в elara.from-address-id !!!
    public static void main(String[] args) {
        SpringApplication.run(ElaraDeliveryApplication.class, args);
    }
}
