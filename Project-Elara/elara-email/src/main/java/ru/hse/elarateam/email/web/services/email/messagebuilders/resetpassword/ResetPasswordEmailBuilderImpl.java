package ru.hse.elarateam.email.web.services.email.messagebuilders.resetpassword;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.postmarkapp.postmark.client.data.model.templates.TemplatedMessage;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import ru.hse.elarateam.email.web.services.email.messagebuilders.general.GeneralMailProperties;

@Slf4j
@ToString
public class ResetPasswordEmailBuilderImpl implements ResetPasswordEmailBuilder {

    /**
     * General properties for all types of email.
     */
    private GeneralMailProperties generalMailProperties;

    /**
     * @see <a href="https://postmarkapp.com/developer/api/templates-api">Postmark API Reference</a>
     */
    @Value("${elara.mailing-service.message-stream}")
    private String messageStream;

    @Value("${elara.mailiing-service.emails.reset-password.template-id}")
    private String templateId;

    @Value("${elara.mailiing-service.emails.reset-password.action-url}")
    private String actionUrl;

    private String toEmail;

    private String username;

    private String resetToken;

    /**
     * Helper class for building JSON Message model.
     * Model is used by Postmark to render email and should contain placeholders according to email template.
     */
    @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    private static class ResetPasswordEmailModel {
        private String name;
        private String productName;
        private String actionUrl;
        private String companyName;
        private String companyAddress;
    }

    public ResetPasswordEmailBuilderImpl() {
        this.generalMailProperties = new GeneralMailProperties();
    }

    @Override
    public ResetPasswordEmailBuilder setUserName(String username) {
        this.username = username;
        return this;
    }

    @Override
    public ResetPasswordEmailBuilder setResetToken(String resetToken) {
        this.resetToken = resetToken;
        return this;
    }

    @Override
    public ResetPasswordEmailBuilder setRecipientEmail(String email) {
        this.toEmail = email;
        return this;
    }

    /**
     * Builds {@link TemplatedMessage} from the data provided.
     *
     * @return {@link TemplatedMessage} that can be sent via {@link com.postmarkapp.postmark.client.ApiClient}
     * @throws JsonProcessingException if provided data cannot be converted to proper JSON message model according to email template.
     */
    @Override
    public TemplatedMessage build() throws JsonProcessingException {
        var message = new TemplatedMessage(generalMailProperties.getFromEmail(), toEmail, templateId, messageStream);
        var model = new ResetPasswordEmailModel(username,
                generalMailProperties.getProductName(),
                actionUrl + "?token=" + resetToken,
                generalMailProperties.getCompanyName(),
                generalMailProperties.getCompanyAddress());

        ObjectMapper objectMapper = new ObjectMapper();

        // serialize model to JSON and set it as model for TemplatedMessage
        String jsonModel = objectMapper.writeValueAsString(model);
        message.setTemplateModel(jsonModel);

        log.debug("TemplatedMessage built(): {}", message);
        return message;
    }
}
