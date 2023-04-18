package ru.hse.elarateam.delivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "shipment_methods")
public class ShipmentMethod {
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
    @Column(nullable = false)
    private Integer tariffCode;

    @NotBlank
    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String tariffName;

    @NotBlank
    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String tariffDescription;

    @NotBlank
    @Column(nullable = false)
    private Integer deliveryMode;

    @NotBlank
    @Column(nullable = false)
    private BigDecimal deliverySum;

    @NotBlank
    @Column(nullable = false)
    private Integer periodMin;

    @NotBlank
    @Column(nullable = false)
    private Integer periodMax;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ShipmentMethod that = (ShipmentMethod) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
