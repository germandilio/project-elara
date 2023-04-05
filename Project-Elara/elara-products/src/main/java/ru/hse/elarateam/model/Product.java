package ru.hse.elarateam.model;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
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

    @Column(unique = true)
    private String upc;

    private String name;

    private BigDecimal price;

    private Integer discount;

    private String description;

    private String brand;

    private Long quantity;

    private String countryOfOrigin;

    private Integer sizeUS;

    private Integer sizeUK;

    private Integer sizeEUR;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Sport> sports;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Color> colors;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Feature> features;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Picture> pictures;

    private Integer height;

    private Integer length;

    private Integer width;

    private Integer weight;

    private Boolean deleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

}
