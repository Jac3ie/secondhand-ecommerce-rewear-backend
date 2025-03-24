package io.muzoo.ssc.project.backend.Buyers;

import io.muzoo.ssc.project.backend.product.Product;
import io.muzoo.ssc.project.backend.product.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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