package io.muzoo.ssc.project.backend.payment;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.product.Product;
import io.muzoo.ssc.project.backend.product.ProductRepository;
import io.muzoo.ssc.project.backend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository; //for updating sold_at when retriving product_id

    public PaymentController(PaymentService paymentService, UserRepository userRepository, ProductRepository productRepository) {
        this.paymentService = paymentService;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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

    @PostMapping("/purchase/{productId}")
    public ResponseEntity<?> purchaseProduct(
            @PathVariable("productId") Long productId,
            @AuthenticationPrincipal UserDetails userDetails) {

        // Lookup the user
        User user = userRepository.findFirstByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Lookup the product by its id
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        Product product = productOpt.get();

        // Check if the product is already sold
        if (product.getSoldAt() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already sold");
        }

        // Update the product: set sold_at to current time and assign the purchaser
        product.setSoldAt(LocalDateTime.now());
        product.setPurchasedBy(user);
        productRepository.save(product);

        return ResponseEntity.ok("Purchase successful");
    }
}