package ru.hse.elarateam.products;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.hse.elarateam.products.model.Product;
import ru.hse.elarateam.products.repositories.ProductsRepository;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class ElaraProductsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraProductsApplication.class, args);
    }

    // initialize database
    @Bean
    public CommandLineRunner initDatabase(ProductsRepository productsRepository) {
        return args -> {
            productsRepository.save(Product.builder()
                    .upc("43cfa7bd-a382-4e6d-8eb2-e0fdfea37cb5")
                    .name("product1")
                    .price(new BigDecimal("1234.99"))
                    .discount(30)
                    .description("description1")
                    .brand("abibas")
                    .quantity(10L)
                    .countryOfOrigin("Russia")
                    .sizeUS(42.0)
                    .sizeUK(42.0)
                    .sizeEUR(42.0)
                    .height(42.0)
                    .length(42.0)
                    .width(42.0)
                    .weight(42.0)
                    .build());
            productsRepository.save(Product.builder()
                    .upc("43cfa7bd-a382-4e6d-8eb2-e0fdfea37adf")
                    .name("product2")
                    .price(new BigDecimal("12354.99"))
                    .discount(30)
                    .description("description2")
                    .brand("noik")
                    .quantity(10L)
                    .countryOfOrigin("Russia")
                    .sizeUS(42.0)
                    .sizeUK(42.0)
                    .sizeEUR(42.0)
                    .height(42.0)
                    .length(42.0)
                    .width(42.0)
                    .weight(42.0)
                    .build());
            productsRepository.flush();
            log.info(productsRepository.findAll().size() + " elarateam were added to database");
        };
    }
}
