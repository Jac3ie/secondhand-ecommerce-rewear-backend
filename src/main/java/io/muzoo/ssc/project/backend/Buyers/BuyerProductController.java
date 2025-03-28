package io.muzoo.ssc.project.backend.Buyers;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.product.Product;
import io.muzoo.ssc.project.backend.product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
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
    @PostMapping("/{id}/purchase")
    public ResponseEntity<?> purchaseProduct(@PathVariable Long id, @RequestParam Long userId) {
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<User> userOptional = userRepository.findById(userId);

        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found.");
        }

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }

        Product product = productOptional.get();

        if (product.getSoldAt() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product already sold.");
        }

        product.setSoldAt(LocalDateTime.now());
        product.setPurchasedBy(userOptional.get());  // Update purchased_by field

        productRepository.save(product);

        return ResponseEntity.ok("Product purchased successfully by user ID: " + userId);
    }

}