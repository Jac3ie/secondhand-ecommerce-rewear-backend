package io.muzoo.ssc.project.backend.Product;

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

    //TODO:not yet have a sold_at param

    @CreationTimestamp
    @Column(name = "sold_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
