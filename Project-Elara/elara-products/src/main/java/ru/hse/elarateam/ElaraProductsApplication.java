package ru.hse.elarateam;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.hse.elarateam.model.Product;
import ru.hse.elarateam.repositories.ProductsRepository;

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
                    .sizeUS(42)
                    .sizeUK(42)
                    .sizeEUR(42)
                    .height(42)
                    .length(42)
                    .width(42)
                    .weight(42)
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
                    .sizeUS(42)
                    .sizeUK(42)
                    .sizeEUR(42)
                    .height(42)
                    .length(42)
                    .width(42)
                    .weight(42)
                    .build());
            productsRepository.flush();
            log.info(productsRepository.findAll().size() + " products were added to database");
        };
    }
}
