package ru.hse.elarateam.email.web.services.email.messagebuilders.verifyemail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.postmarkapp.postmark.client.data.model.templates.TemplatedMessage;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.hse.elarateam.email.web.services.email.messagebuilders.general.Email;
import ru.hse.elarateam.email.web.services.email.messagebuilders.verifyemail.model.VerifyEmailModel;

@RequiredArgsConstructor
@Slf4j
@ToString
@Component
@Scope("prototype")
public class VerifyEmail implements Email {

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
    private final String verificationToken;

    /**
     * Builds {@link TemplatedMessage} from the data provided.
     *
     * @return {@link TemplatedMessage} that can be sent via {@link com.postmarkapp.postmark.client.ApiClient}
     * @throws JsonProcessingException if provided data cannot be converted to proper JSON message model according to email template.
     */
    @Override
    public TemplatedMessage build() throws JsonProcessingException {
        var message = new TemplatedMessage(fromEmail, toEmail, templateId, messageStream);
        var model = new VerifyEmailModel(username,
                productName,
                toEmail,
                actionUrl + "/" + verificationToken,
                companyName,
                companyAddress);

        ObjectMapper objectMapper = new ObjectMapper();

        // serialize model to JSON and set it as model for TemplatedMessage
        String jsonModel = objectMapper.writeValueAsString(model);
        log.trace("Email model: {}", jsonModel);

        message.setTemplateModel(jsonModel);


        log.debug("TemplatedMessage built(): from {}, to {}, metadata: {}, templateID: {}, templateModel: {}, attachments: {}",
                message.getFrom(),
                message.getTo(),
                message.getMetadata(),
                message.getTemplateId(),
                message.getTemplateModel(),
                message.getAttachments());

        return message;
    }
}
