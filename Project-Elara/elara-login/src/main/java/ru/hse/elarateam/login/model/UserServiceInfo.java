package ru.hse.elarateam.login.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;
import ru.hse.elarateam.login.web.converters.PasswordConverter;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE user_service_info SET deleted=true WHERE id=?")
@Where(clause = "deleted=false")
@Table(name = "user_service_info", indexes = {
        @Index(name = "idx_userserviceinfo_login_unq", columnList = "login", unique = true)
})
public class UserServiceInfo {
    @Id
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(columnDefinition = "varchar(255)", nullable = false, unique = true)
    private String login;

    @Convert(converter = PasswordConverter.class)
    @Column(columnDefinition = "varchar(256)", nullable = false)
    private String password;

    @Column(length = 128, columnDefinition = "varchar(128)", unique = true)
    private String emailVerificationToken;

    @Column(length = 128, columnDefinition = "varchar(128)", unique = true)
    private String passwordResetToken;

    @Column(columnDefinition = "timestamp")
    private Timestamp passwordResetTokenExpiredAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Builder.Default
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = Boolean.FALSE;

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
