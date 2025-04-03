package io.muzoo.ssc.project.backend.orders;

import io.muzoo.ssc.project.backend.User;
import io.muzoo.ssc.project.backend.UserRepository;
import io.muzoo.ssc.project.backend.product.Product;
import io.muzoo.ssc.project.backend.product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderController(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/history")
    public ResponseEntity<List<Product>> getOrderHistory(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findFirstByUsername(userDetails.getUsername());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<Product> products = productRepository.findByPurchasedBy(user);
        return ResponseEntity.ok(products);
    }
}
