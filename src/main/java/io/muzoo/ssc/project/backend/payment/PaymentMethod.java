package io.muzoo.ssc.project.backend.payment;

import io.muzoo.ssc.project.backend.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "tbl_payment_method")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", length = 10, nullable = false)
    private String cardNumber;

    // Masked version, e.g. 111X-XXXX-XX
    @Column(name = "masked_card", length = 20, nullable = false)
    private String maskedCard;

    @Column(name = "cardholder_name", nullable = false)
    private String cardholderName;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}