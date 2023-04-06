package ru.hse.elarateam.web.services.email.messagebuilders.resetpassword;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postmarkapp.postmark.client.data.model.templates.TemplatedMessage;
import ru.hse.elarateam.web.services.email.messagebuilders.general.EmailBuilder;

/**
 * Builder that handles creation of {@link TemplatedMessage} for reset password email.
 */
public interface ResetPasswordEmailBuilder extends EmailBuilder {
    ResetPasswordEmailBuilder setUserName(String userName);

    ResetPasswordEmailBuilder setResetToken(String resetToken);

    ResetPasswordEmailBuilder setRecipientEmail(String recipientEmail);

    /**
     * Builds {@link TemplatedMessage} from the data provided.
     *
     * @return {@link TemplatedMessage} that can be sent via {@link com.postmarkapp.postmark.client.ApiClient}
     * @throws JsonProcessingException if provided data cannot be converted to proper JSON message model according to email template.
     */
    TemplatedMessage build() throws JsonProcessingException;
}
