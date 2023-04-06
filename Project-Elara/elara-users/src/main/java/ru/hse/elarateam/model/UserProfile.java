package ru.hse.elarateam.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import java.sql.Timestamp;
import java.util.Date;
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
@Table(name = "user_profiles")
public class UserProfile {
    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private UserServiceInfo userServiceInfo;

    // to do reference to user service info login
    @Column(columnDefinition = "varchar(255)", unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String firstName;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String lastName;

    @Column(columnDefinition = "varchar(512)")
    private String pictureUrl;

    @Column(columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

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
        UserProfile that = (UserProfile) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
