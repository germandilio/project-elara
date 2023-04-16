package ru.hse.elarateam.adminconsole.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    // todo решить, нужно ли уникальное название цвета

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // при откате транзакции счетчик все равно увеличивается на 1
    private Long id;

    @NotBlank
    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String name;

    @NotBlank
    @Column(length = 7, columnDefinition = "varchar(7)", nullable = false)
    private String hex;

    @ManyToMany(mappedBy = "colors", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonBackReference
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
