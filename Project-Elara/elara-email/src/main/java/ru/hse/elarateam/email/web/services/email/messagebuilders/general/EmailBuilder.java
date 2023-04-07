package ru.hse.elarateam.email.web.services.email.messagebuilders.general;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.postmarkapp.postmark.client.data.model.templates.TemplatedMessage;

public interface EmailBuilder {
    /**
     * Builds {@link TemplatedMessage} from the data provided.
     *
     * @return {@link TemplatedMessage} that can be sent via {@link com.postmarkapp.postmark.client.ApiClient}
     * @throws JsonProcessingException if provided data cannot be converted to proper JSON message model according to email template.
     */
    TemplatedMessage build() throws JsonProcessingException;
}
