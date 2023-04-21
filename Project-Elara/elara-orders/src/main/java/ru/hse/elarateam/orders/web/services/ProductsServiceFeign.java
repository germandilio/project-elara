package ru.hse.elarateam.orders.web.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.orders.dto.request.OrderRequestDTO;
import ru.hse.elarateam.orders.dto.response.ProductResponseDTO;
import ru.hse.elarateam.orders.web.feign.ProductsServiceFeignClient;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductsServiceFeign {
    private final ProductsServiceFeignClient productsServiceFeignClient;

    public ResponseEntity<List<ProductResponseDTO>> allocateProducts(OrderRequestDTO orderRequestDTO) {
        try {
            var response = productsServiceFeignClient.allocateProducts(orderRequestDTO);
//            if(response.getBody().getData().equals(orderRequestDTO.getPositions()));
            // todo проверить на соответсвие листы

            if (!response.hasBody()) {
                log.error("Null response from products service.");
                throw new IllegalStateException("Null response from products service.");
            }

            return response;

        } catch (FeignException.Unauthorized ex) {
            log.error("Authorization of service token in products failed.");
            throw new IllegalStateException("Authorization of service token in products failed.", ex);
        } catch (FeignException.BadRequest ex) {
            log.error("Bad request to products.", ex);
        } catch (FeignException.InternalServerError ex) {
            log.error("Products service is unavailable.");
            throw new IllegalStateException("Products service is unavailable.", ex);
        } catch (Exception ex) {
            log.error("Error allocating products: ", ex);
            throw new IllegalStateException("Error allocating products: ", ex);
        }
        return null;
    }

    public ResponseEntity<List<UUID>> deallocateProducts(OrderRequestDTO orderRequestDTO) {
        try {
            var response = productsServiceFeignClient.deallocateProducts(orderRequestDTO);
//            if(response.getBody().getData().equals(orderRequestDTO.getPositions()));
            // todo проверить на соответсвие листы

            if (!response.hasBody()) {
                log.error("Null response from products service.");
                throw new IllegalStateException("Null response from products service.");
            }

            return response;

        } catch (FeignException.Unauthorized ex) {
            log.error("Authorization of service token in products failed.");
            throw new IllegalStateException("Authorization of service token in products failed.", ex);
        } catch (FeignException.BadRequest ex) {
            log.error("Bad request to products.", ex);
        } catch (FeignException.InternalServerError ex) {
            log.error("Products service is unavailable.");
            throw new IllegalStateException("Products service is unavailable.", ex);
        } catch (Exception ex) {
            log.error("Error allocating products: ", ex);
            throw new IllegalStateException("Error deallocating products: ", ex);
        }
        return null;
    }
}
