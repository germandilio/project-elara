package ru.hse.elarateam.model;


import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import ru.hse.elarateam.web.converters.PasswordConverter;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Where(clause = "deleted = false")
@Table(name = "user_service_info")
public class UserServiceInfo {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String login;

    @Convert(converter = PasswordConverter.class)
    @Column(columnDefinition = "varchar(80)", nullable = false)
    private String password;

    @Column(length = 128, columnDefinition = "varchar(128)", unique = true)
    private String emailVerificationToken;

    @Column(length = 128, columnDefinition = "varchar(128)", unique = true)
    private String passwordResetToken;

    @Column(columnDefinition = "timestamp")
    private Timestamp passwordResetTokenExpirationTime;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp creationTime;

    @UpdateTimestamp
    private Timestamp lastUpdateTime;

    @Version
    private Integer version;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private UserProfile userProfile;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserServiceInfo that = (UserServiceInfo) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
