package ru.hse.elarateam.products.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
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
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JdbcTypeCode(java.sql.Types.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String upc;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    /**
     * Discount in percents
     */
    private Integer discount;

    @Column(length = 1024, columnDefinition = "varchar(1024)", nullable = false)
    private String description;

    @Column(length = 64, columnDefinition = "varchar(64)", nullable = false)
    private String brand;

    @Column(nullable = false)
    private Long quantity;

    @Column(length = 64, columnDefinition = "varchar(64)")
    private String countryOfOrigin;

    @Column(nullable = false)
    private Integer sizeUS;

    @Column(nullable = false)
    private Integer sizeUK;

    @Column(nullable = false)
    private Integer sizeEUR;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_sports",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "sports_id"))
    @ToString.Exclude
    private Set<Sport> sports = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_colors",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "colors_id"))
    @ToString.Exclude
    private Set<Color> colors = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "products_features",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "features_id"))
    @ToString.Exclude
    private Set<Feature> features = new LinkedHashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<Picture> pictures = new LinkedHashSet<>();

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Integer length;

    @Column(nullable = false)
    private Integer width;

    @Column(nullable = false)
    private Integer weight;

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
