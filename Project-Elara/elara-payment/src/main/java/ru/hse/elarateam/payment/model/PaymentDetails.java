package ru.hse.elarateam.payment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import ru.hse.elarateam.payment.model.status.PaymentStatus;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "payment_details")
public class PaymentDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID orderId;

    @NotBlank
    @Column(nullable = false)
    private PaymentStatus status;

    @NotBlank
    @Column(nullable = false)
    private String userEmail;

    @NotBlank
    @Column(nullable = false)
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PaymentDetails that = (PaymentDetails) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
