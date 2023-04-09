package ru.hse.elarateam.email.configs;

import com.postmarkapp.postmark.Postmark;
import com.postmarkapp.postmark.client.ApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PostmarkClientConfiguration {

    @Value("${elara.mailing-service.token}")
    private String apiToken;

    @Bean
    public ApiClient postmarkApiClient() {
        return Postmark.getApiClient(apiToken);
    }

}
