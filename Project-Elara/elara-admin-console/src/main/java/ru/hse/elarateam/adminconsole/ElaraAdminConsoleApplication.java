package ru.hse.elarateam.adminconsole;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ru.hse.elarateam.adminconsole.model.Color;
import ru.hse.elarateam.adminconsole.model.Product;
import ru.hse.elarateam.adminconsole.repositories.ColorsRepository;
import ru.hse.elarateam.adminconsole.repositories.ProductsRepository;

import java.util.HashSet;
import java.util.Set;
@EnableFeignClients
@SpringBootApplication
public class ElaraAdminConsoleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElaraAdminConsoleApplication.class, args);
    }

//     initialize database
//    @Bean
//    public CommandLineRunner initDatabase(ProductsRepository productsRepository,
//                                          ColorsRepository colorsRepository) {
//        return args -> {
//            colorsRepository.save(Color.builder().name("red").hex("#FF0000").build());
//            colorsRepository.save(Color.builder().name("green").hex("#00FF00").build());
//            colorsRepository.flush();
//            var colors = colorsRepository.findAll();
//            productsRepository.save(Product.builder()
////                    .upc("43cfa7bd-a382-4e6d-8eb2-e0fdfea37cb5")
//                    .name("product1")
////                    .price(new BigDecimal("1234.99"))
////                    .discount(30)
////                    .description("description1")
////                    .brand("abibas")
////                    .quantity(10L)
////                    .countryOfOrigin("Russia")
////                    .sizeUS(42.0)
////                    .sizeUK(42.0)
////                    .sizeEUR(42.0)
////                    .height(42.0)
////                    .length(42.0)
////                    .width(42.0)
////                    .weight(42.0)
//                    .colors(new HashSet<>(colors))
//                    .build());
//            productsRepository.flush();
//        };
//    }
}
