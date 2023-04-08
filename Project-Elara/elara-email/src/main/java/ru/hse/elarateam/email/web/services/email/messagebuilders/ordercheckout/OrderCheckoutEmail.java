package ru.hse.elarateam.email.web.services.email.messagebuilders.ordercheckout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postmarkapp.postmark.client.data.model.templates.TemplatedMessage;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.email.dto.OrderCheckoutDTO;
import ru.hse.elarateam.email.web.services.email.messagebuilders.general.EmailBuilder;
import ru.hse.elarateam.email.web.services.email.messagebuilders.ordercheckout.model.OrderCheckoutModel;

@Slf4j
@ToString
@RequiredArgsConstructor
@Component
@Scope("prototype")
public class OrderCheckoutEmail implements EmailBuilder {
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

    private final String toEmail;

    private final String username;

    private final OrderCheckoutDTO orderDetails;

    /**
     * Helper class for building JSON Message model.
     * Model is used by Postmark to render email and should contain placeholders according to email template.
     */

    /**
     * Builds {@link TemplatedMessage} from the data provided.
     *
     * @return {@link TemplatedMessage} that can be sent via {@link com.postmarkapp.postmark.client.ApiClient}
     * @throws JsonProcessingException if provided data cannot be converted to proper JSON message model according to email template.
     */
    @Override
    public TemplatedMessage build() throws JsonProcessingException {
        var message = new TemplatedMessage(fromEmail, toEmail, templateId, messageStream);

        var model = new OrderCheckoutModel(
                username,
                productName,
                orderDetails.getOrderId().toString(),
                orderDetails.getDate(),
                orderDetails.getTotalHeight().toString(),
                orderDetails.getTotalLength().toString(),
                orderDetails.getTotalWidth().toString(),
                String.format("%.2f", orderDetails.getTotalWeight()),
                actionUrl + "/" + orderDetails.getOrderId(),
                companyName,
                companyAddress
        );

        ObjectMapper objectMapper = new ObjectMapper();

        // serialize model to JSON and set it as model for TemplatedMessage
        String jsonModel = objectMapper.writeValueAsString(model);
        message.setTemplateModel(jsonModel);

        log.debug("TemplatedMessage built(): {}", message);
        return message;
    }
}
