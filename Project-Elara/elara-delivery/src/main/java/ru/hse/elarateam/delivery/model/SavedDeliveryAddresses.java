package ru.hse.elarateam.delivery.model;

import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
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
@Table(name = "user_delivery_addresses")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE user_delivery_addresses SET deleted = true WHERE id = ?")
public class SavedDeliveryAddresses {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 36, columnDefinition = "varchar(36)", nullable = false)
    private UUID userID;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "user_delivery_addresses_addresses",
            joinColumns = @JoinColumn(name = "saved_delivery_addresses_id"),
            inverseJoinColumns = @JoinColumn(name = "addresses_id"))
    private Set<Address> addresses = new LinkedHashSet<>();

    @Column(columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean deleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SavedDeliveryAddresses that = (SavedDeliveryAddresses) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
