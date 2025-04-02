package io.muzoo.ssc.project.backend.product;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    //modified string length to adapt when Json String is too long, typically when images are more than 3 from test
    @Column(name = "pic_url", columnDefinition = "TEXT")
    private String pic_url;

    @Column(name = "sold_at")
    @JsonProperty("sold_at")
    private LocalDateTime soldAt;

    @ManyToOne
    @JoinColumn(name = "purchased_by", referencedColumnName = "id")
    private User purchasedBy;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

}