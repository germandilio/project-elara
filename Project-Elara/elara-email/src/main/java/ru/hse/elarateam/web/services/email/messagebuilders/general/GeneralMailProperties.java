package ru.hse.elarateam.web.services.email.messagebuilders.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * General properties for all types of transactional emails.
 * <p>
 * Contains info about company, product, etc.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class GeneralMailProperties {
    @Value("${elara.mailing-service.emails.from-email}")
    private String fromEmail;

    @Value("${elara.mailiing-service.emails.product-name}")
    private String productName;

    @Value("${elara.mailiing-service.emails.company-name}")
    private String companyName;

    @Value("${elara.mailiing-service.emails.company-address}")
    private String companyAddress;
}
