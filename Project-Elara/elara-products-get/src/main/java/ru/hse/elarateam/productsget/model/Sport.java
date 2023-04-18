package ru.hse.elarateam.productsget.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "sports")
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32, columnDefinition = "varchar(32)", nullable = false)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "sports", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Product> products = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sport sport = (Sport) o;
        return getId() != null && Objects.equals(getId(), sport.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
