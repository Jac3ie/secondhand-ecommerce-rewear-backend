package io.muzoo.ssc.project.backend.address;

import jakarta.persistence.*;
import io.muzoo.ssc.project.backend.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tbl_address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "house_no", nullable = false, length = 50)
    private String houseNo;

    @Column(nullable = false, length = 100)
    private String province;

    @Column(name = "postal_code", nullable = false, length = 5)
    private String postalCode;

    @Column(name = "is_default", columnDefinition = "TINYINT(1) DEFAULT 1")
    private Boolean isDefault;
}
