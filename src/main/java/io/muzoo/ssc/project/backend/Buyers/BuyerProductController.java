package io.muzoo.ssc.project.backend.Buyers;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.product.Product;
import io.muzoo.ssc.project.backend.product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class BuyerProductController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public BuyerProductController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(value = "query", required = false) String query) {
        List<Product> products = productRepository.findAll();

        if (query != null && !query.isEmpty()) {
            String lowerQuery = query.toLowerCase();
            products = products.stream()
                    .filter(product -> isConsecutiveMatch(product.getName().toLowerCase(), lowerQuery))
                    .toList();
        }

        return ResponseEntity.ok(products);
    }

    private boolean isConsecutiveMatch(String name, String query) {
        int index = 0;
        for (char c : name.toCharArray()) {
            if (c == query.charAt(index)) index++;
            if (index == query.length()) return true;
        }
        return false;
    }

    @PostMapping("/{productId}/purchase")
    public ResponseEntity<?> purchaseProduct(@PathVariable Long productId, @RequestBody Map<String, Object> payload) {
        if (!payload.containsKey("userId") || !payload.containsKey("sold_at")) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Missing userId or sold_at"));
        }

        try {
            Long userId = ((Number) payload.get("userId")).longValue();
            LocalDateTime soldAt = LocalDateTime.parse((String) payload.get("sold_at"));

            Optional<User> user = userRepository.findById(userId);
            Optional<Product> product = productRepository.findById(productId);

            if (user.isPresent() && product.isPresent()) {
                Product purchasedProduct = product.get();

                if (purchasedProduct.getSoldAt() != null) { // Prevent duplicate purchases
                    return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Product already sold"));
                }

                purchasedProduct.setSoldAt(soldAt);
                purchasedProduct.setPurchasedBy(user.get());
                productRepository.save(purchasedProduct);

                return ResponseEntity.ok(Map.of("success", true, "message", "Purchase successful"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "User or product not found"));
            }
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "Invalid date format for sold_at"));
        }
    }

}