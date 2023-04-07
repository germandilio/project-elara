package ru.hse.elarateam.email.web.services.email.messagebuilders.verifyemail;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postmarkapp.postmark.client.data.model.templates.TemplatedMessage;
import ru.hse.elarateam.email.web.services.email.messagebuilders.general.EmailBuilder;

/**
 * Builder that handles creation of {@link TemplatedMessage} for verification email.
 */
public interface VerifyEmailBuilder extends EmailBuilder {
    VerifyEmailBuilder setUserName(String userName);

    VerifyEmailBuilder setVerificationToken(String verificationToken);

    VerifyEmailBuilder setRecipientEmail(String recipientEmail);

    /**
     * Builds {@link TemplatedMessage} from the data provided.
     *
     * @return {@link TemplatedMessage} that can be sent via {@link com.postmarkapp.postmark.client.ApiClient}
     * @throws JsonProcessingException if provided data cannot be converted to proper JSON message model according to email template.
     */
    TemplatedMessage build() throws JsonProcessingException;
}
