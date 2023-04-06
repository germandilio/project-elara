package ru.hse.products.model;

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
@Table(name = "colors")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String name;

    @Column(length = 7, columnDefinition = "varchar(7)", nullable = false)
    private String hex;

    @ManyToMany(mappedBy = "colors", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Product> products = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Color color = (Color) o;
        return getId() != null && Objects.equals(getId(), color.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
