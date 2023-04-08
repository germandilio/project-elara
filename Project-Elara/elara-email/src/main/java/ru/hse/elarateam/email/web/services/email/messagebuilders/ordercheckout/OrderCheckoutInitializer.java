package ru.hse.elarateam.email.web.services.email.messagebuilders.ordercheckout;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.email.dto.OrderCheckoutDTO;


@ToString
@Component
public class OrderCheckoutInitializer {
    private final String fromEmail;

    private final String productName;

    private final String companyName;

    private final String companyAddress;

    /**
     * @see <a href="https://postmarkapp.com/developer/api/templates-api">Postmark API Reference</a>
     */
    private final String messageStream;

    private final int templateId;

    private final String actionUrl;

    public OrderCheckoutInitializer(
            @Value("${elara.mailing-service.emails.from-email}") String fromEmail,
            @Value("${elara.mailiing-service.emails.product-name}") String productName,
            @Value("${elara.mailiing-service.emails.company-name}") String companyName,
            @Value("${elara.mailiing-service.emails.company-address}") String companyAddress,
            @Value("${elara.mailing-service.message-stream}") String messageStream,
            @Value("${elara.mailiing-service.emails.order-checkout.template-id}") int templateId,
            @Value("${elara.mailiing-service.emails.order-checkout.action-url}") String actionUrl) {
        this.fromEmail = fromEmail;
        this.productName = productName;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.messageStream = messageStream;
        this.templateId = templateId;
        this.actionUrl = actionUrl;
    }

    public OrderCheckoutEmail init(String toEmail, String username, OrderCheckoutDTO orderDetails) {
        return new OrderCheckoutEmail(fromEmail,
                productName,
                companyName,
                companyAddress,
                messageStream,
                templateId,
                actionUrl,
                toEmail,
                username,
                orderDetails);
    }
}
