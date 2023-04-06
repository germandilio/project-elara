package ru.hse.elarateam.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Where(clause = "deleted = false")
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false, unique = true)
    private String role;

    @Column(length = 1024, columnDefinition = "varchar(1024)")
    private String roleDescription;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationTime;

    @UpdateTimestamp
    private Timestamp lastUpdateTime;

    @Version
    private Integer version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return getId() != null && Objects.equals(getId(), role.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
