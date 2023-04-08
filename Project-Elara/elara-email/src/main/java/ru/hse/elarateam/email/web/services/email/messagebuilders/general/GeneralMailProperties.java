package ru.hse.elarateam.email.web.services.email.messagebuilders.general;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * General properties for all types of transactional emails.
 * <p>
 * Contains info about company, product, etc.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@Component
public class GeneralMailProperties {
}
