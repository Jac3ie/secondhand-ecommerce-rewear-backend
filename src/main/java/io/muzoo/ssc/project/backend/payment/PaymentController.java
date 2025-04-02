package io.muzoo.ssc.project.backend.payment;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserRepository userRepository;

    public PaymentController(PaymentService paymentService, UserRepository userRepository) {
        this.paymentService = paymentService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<PaymentMethod> getPaymentMethod(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findFirstByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return paymentService.getPaymentMethodByUser(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> savePaymentMethod(
            @RequestBody PaymentMethodDTO request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findFirstByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(401).body("User not found");
        }

        try {
            PaymentMethod savedPayment = paymentService.savePaymentMethod(request, user);
            return ResponseEntity.ok(savedPayment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}