package ru.hse.elarateam.productsget;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport
@SpringBootApplication
public class ProductsGetApplication {
    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(ProductsGetApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Products Get API")
                        .description("Public API for getting products")
                        .version("1.0"));
    }
}
