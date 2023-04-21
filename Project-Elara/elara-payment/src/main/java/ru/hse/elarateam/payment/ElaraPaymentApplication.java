package ru.hse.elarateam.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ElaraPaymentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraPaymentApplication.class, args);
    }
}
