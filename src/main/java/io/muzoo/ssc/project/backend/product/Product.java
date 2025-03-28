package io.muzoo.ssc.project.backend.product;

import io.muzoo.ssc.project.backend.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tbl_product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;
    private double price;
    private String pic_url; //store the url in string

    @Column(name = "sold_at")
    private LocalDateTime soldAt;

    @ManyToOne
    @JoinColumn(name = "purchased_by", referencedColumnName = "id")
    private User purchasedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}