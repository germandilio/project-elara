package ru.hse.elarateam.products;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.hse.elarateam.products.model.Product;
import ru.hse.elarateam.products.web.repositories.ProductsRepository;
import ru.hse.elarateam.products.web.services.jwt.ServiceTokenUtilsImpl;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class ElaraProductsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraProductsApplication.class, args);
    }
}
