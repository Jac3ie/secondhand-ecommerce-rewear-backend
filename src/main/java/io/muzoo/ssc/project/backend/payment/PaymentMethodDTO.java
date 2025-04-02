package io.muzoo.ssc.project.backend.payment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDTO {

    @NotBlank(message = "Cardholder name is required")
    private String cardholderName;

    @NotBlank(message = "Card number is required")
    private String cardNumber;


    private int expirationMonth;
    private int expirationYear;

    @NotBlank(message = "CVC is required")
    private String cvc; // For transaction only, not stored
}