//package ru.hse.elarateam.products.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.Hibernate;
//
//import java.util.Objects;
//
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Builder
//@Table(name = "pictures")
//public class Picture {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(length = 256, columnDefinition = "varchar(256)", nullable = false)
//    private String uri;
//
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false, unique = true)
//    @ToString.Exclude
//    private Product product;
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
//        Picture picture = (Picture) o;
//        return getId() != null && Objects.equals(getId(), picture.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }
//}
