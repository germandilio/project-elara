package ru.hse.elarateam.adminconsole.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
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
@Table(name = "products")
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id = ?")
public class Product {
    // todo restore nullable
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false)
    private UUID id;

    @Column(length = 64, columnDefinition = "varchar(64)")
    private String upc;

    @Column(length = 64, columnDefinition = "varchar(64)")
    private String name;

    @Column()
    private BigDecimal price;

    /**
     * Discount in percents
     */
    @Min(1)
    @Max(100)
    private Integer discount;

    @Column(length = 1024, columnDefinition = "varchar(1024)")
    private String description;

    @Column(length = 64, columnDefinition = "varchar(64)")
    private String brand;

    @Column()
    private Long quantity;

    @Column(length = 64, columnDefinition = "varchar(64)")
    private String countryOfOrigin;

    @Column()
    private Double sizeUS;

    @Column()
    private Double sizeUK;

    @Column()
    private Double sizeEUR;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_sports",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sports_id"))
    @ToString.Exclude
    private Set<Sport> sports = new LinkedHashSet<>();

    /**
     * doesn't allow saving transient objects
     * doesn't allow saving non-existing ids
     */
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_colors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "colors_id"))
    @ToString.Exclude
    private Set<Color> colors = new LinkedHashSet<>();

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_features",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "features_id"))
    @ToString.Exclude
    private Set<Feature> features = new LinkedHashSet<>();

    @ToString.Exclude
    @JsonManagedReference
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> pictures = new LinkedHashSet<>();

    @Column()
    private Double height;

    @Column()
    private Double length;

    @Column()
    private Double width;

    @Column()
    private Double weight;

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
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
