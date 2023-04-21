package ru.hse.elarateam.users.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Data
@Builder
public class OrderCheckoutDTO {
    /**
     * Order ID.
     */
    private UUID orderId;

    /**
     * Total order price with discount.
     */
    private BigDecimal totalWIthDiscount;

    /**
     * Total order price without discount.
     */
    private BigDecimal total;

//    TODO: add after payment and order microservices
//    private ShipmentDetailsDTO shipmentDetails;
//    private PaymentInfoDTO paymentInfo;

    /**
     * Total order weight.
     * Information for delivery service.
     */
    private Integer totalHeight;

    /**
     * Total order length.
     * Information for delivery service.
     */
    private Integer totalLength;

    /**
     * Total order width.
     * Information for delivery service.
     */
    private Integer totalWidth;

    /**
     * Total order weight.
     * Information for delivery service.
     */
    private Double totalWeight;

    // TODO define which date tupe to use (Timestamp or Date sql)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;
}
