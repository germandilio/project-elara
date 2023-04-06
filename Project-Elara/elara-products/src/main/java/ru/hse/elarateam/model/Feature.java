package ru.hse.elarateam.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "features")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String name;

    @Column(length = 256, columnDefinition = "varchar(256)", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "features", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Product> products = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Feature feature = (Feature) o;
        return getId() != null && Objects.equals(getId(), feature.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
