package ru.hse.elarateam.email.web.services.email.messagebuilders.ordercheckout.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OrderCheckoutModel {
    private String name;
    private String productName;
    private String orderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date date;
    private String totalHeight;
    private String totalLength;
    private String totalWidth;
    private String totalWeight;
    private String actionUrl;
    private String companyName;
    private String companyAddress;

}
