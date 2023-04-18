package ru.hse.elarateam.orders.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID userId;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String postalCode;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String city;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String country;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String street;

    @Column(length = 32, columnDefinition = "varchar(32)", nullable = false)
    private String buildingNumber;

    @Column(length = 32, columnDefinition = "varchar(32)", nullable = false)
    private String apartmentNumber;

    @Column(length = 32, columnDefinition = "varchar(32)", nullable = false)
    private String entranceNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Address address = (Address) o;
        return getId() != null && Objects.equals(getId(), address.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
