package ru.hse.elarateam.delivery.web.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.hse.elarateam.delivery.dto.info.AddressInfoDTO;
import ru.hse.elarateam.delivery.model.ShipmentMethod;
import ru.hse.elarateam.delivery.web.feign.SDEKFeignClient;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SDEKFeignService {
    public List<ShipmentMethod> getShipmentMethods(Double totalHeight,
                                                   Double totalWidth,
                                                   Double totalLength,
                                                   Double totalWeight,
                                                   AddressInfoDTO fromAddress,
                                                   AddressInfoDTO toAddress) {
        return List.of(
                ShipmentMethod.builder()
                        .deliverySum(new BigDecimal(322L))
                        .deliveryMode(2)
                        .tariffCode(321)
                        .tariffName("Самовывоз")
                        .tariffDescription("Самовывоз из магазина или со склада.")
                        .periodMin(1)
                        .periodMax(20)
                        .build(),

                ShipmentMethod.builder()
                        .deliverySum(new BigDecimal(1200L))
                        .deliveryMode(3)
                        .tariffCode(323)
                        .tariffName("Доставка до двери")
                        .tariffDescription("Доставка курьером на выбранный адрес.")
                        .periodMin(5)
                        .periodMax(20)
                        .build(),

                ShipmentMethod.builder()
                        .deliverySum(new BigDecimal(9999L))
                        .deliveryMode(1)
                        .tariffCode(95)
                        .tariffName("Доставка бархатных тяг")
                        .tariffDescription(
                                "Специальное предложение для состоятельных клиентов Elara e-commerce platform." +
                                        "Самая быстрая доставка к западу от Миссури." +
                                        "За день в любую точку мира: от арктических полярных льдов до джунглей Танзании." +
                                        "По возможностям доставки в воронежскую область уточняйте у оператора.")
                        .periodMin(1)
                        .periodMax(1)
                        .build());
    }
}
