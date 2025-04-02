package io.muzoo.ssc.project.backend.service;

import io.muzoo.ssc.project.backend.payment.PaymentMethod;
import io.muzoo.ssc.project.backend.payment.PaymentMethodDTO;
import io.muzoo.ssc.project.backend.payment.PaymentMethodRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.YearMonth;
import java.util.Optional;

import io.muzoo.ssc.project.backend.User;

@Service
public class PaymentService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public Optional<PaymentMethod> getPaymentMethodByUser(User user) {
        return paymentMethodRepository.findByUser(user);
    }

    private void validateCard(PaymentMethodDTO request) {
        // Basic validation
        if (request.getCardNumber().length() != 10) {
            throw new IllegalArgumentException("Card number must be 10 digits");
        }

        if (!request.getCardNumber().matches("\\d+")) {
            throw new IllegalArgumentException("Card number must be numeric");
        }

        if (request.getCvc().length() != 3 || !request.getCvc().matches("\\d+")) {
            throw new IllegalArgumentException("Invalid CVC");
        }

        if (!request.getCardNumber().startsWith("111")) {
            throw new IllegalArgumentException("Only cards starting with 111 are accepted");
        }
    }

    public PaymentMethod savePaymentMethod(PaymentMethodDTO request, User user) {
        validateCard(request);

        // Delete existing payment method
        paymentMethodRepository.findByUser(user).ifPresent(paymentMethodRepository::delete);

        // Create new entity
        PaymentMethod pm = new PaymentMethod();
        pm.setUser(user);
        pm.setCardNumber(request.getCardNumber());
        pm.setMaskedCard(generateMasked(request.getCardNumber()));
        pm.setCardholderName(request.getCardholderName());
        pm.setExpirationDate(convertExpiry(request));

        return paymentMethodRepository.save(pm);
    }

    private String generateMasked(String card) {
        return card.substring(0, 3) + "X-XXXX-" + card.substring(9, 10);
    }

    private Date convertExpiry(PaymentMethodDTO request) {
        YearMonth ym = YearMonth.of(request.getExpirationYear(), request.getExpirationMonth());
        return Date.valueOf(ym.atEndOfMonth());
    }
}
