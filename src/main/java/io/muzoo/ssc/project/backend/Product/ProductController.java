package io.muzoo.ssc.project.backend.Product;

import io.muzoo.ssc.project.backend.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('admin')")
public class ProductController {

    private final ProductRepository productRepository;
    private final StorageService storageService;

    public ProductController(ProductRepository productRepository, StorageService storageService) {
        this.productRepository = productRepository;
        this.storageService = storageService;
    }

    //here is the actual endpoint to save form data from frontend to db
    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestParam MultipartFile image,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam(required = false) String description) {
        try{
            //upload image and get the url from bucket
            String imgUrl = storageService.uploadFile(image);

            //initiate Product instance for save
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);
            product.setPic_url(imgUrl);

            //use JpaRepo to save it
            productRepository.save(product);

            return ResponseEntity.ok(product);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
        }
    }

    //endpoint for get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll(); //make use of .findAll() from JpaRepo
        return ResponseEntity.ok(products);
    }
}
