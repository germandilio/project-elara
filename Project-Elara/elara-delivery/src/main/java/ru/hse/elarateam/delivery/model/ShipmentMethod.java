package ru.hse.elarateam.delivery.model;

import jakarta.persistence.*;
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

    @Column(nullable = false)
    private Integer tariffCode;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String tariffName;

    @Column(length = 512, columnDefinition = "varchar(512)", nullable = false)
    private String tariffDescription;

    @Column(nullable = false)
    private Integer deliveryMode;

    @Column(nullable = false)
    private BigDecimal deliverySum;

    @Column(nullable = false)
    private Integer periodMin;

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
