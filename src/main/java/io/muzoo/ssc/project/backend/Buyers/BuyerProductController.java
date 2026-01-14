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

    public BuyerProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

}