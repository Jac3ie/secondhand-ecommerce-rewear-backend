package io.muzoo.ssc.project.backend.product;

import io.muzoo.ssc.project.backend.service.StorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
            System.out.println("Uploading image...");
            String imgUrl = storageService.uploadFile(image);
            System.out.println("Image uploaded: " + imgUrl);
            // Debug : Check if image uploads successfully
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create product: " + e.getMessage());
        }
    }

    //endpoint for get all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll(); //make use of .findAll() from JpaRepo
        return ResponseEntity.ok(products);
    }

    //endpoint for editing product in admin page
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long id,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam String name,
            @RequestParam double price,
            @RequestParam(required = false) String description) {
        return productRepository.findById(id).map(product -> {
            product.setName(name);
            product.setPrice(price);
            product.setDescription(description);

            //check image is provided or not
            //if so then replaced with bucket current one, and update
            if (image != null && !image.isEmpty()) {
                try{
                    String imgUrl = storageService.uploadFile(image); // TODO:not yet delete original one, need another func in storageService
                    product.setPic_url(imgUrl);
                }catch (IOException e) {
                    return ResponseEntity.badRequest().body("File upload failed: " + e.getMessage());
                }
            }
            productRepository.save(product);
            return ResponseEntity.ok(product);
        }).orElse(ResponseEntity.notFound().build());
    }

    //endpoint for delete product
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        //check exist and delete
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.ok("Product deleted successfully");
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/products/{id}/sold")
    public ResponseEntity<?> markProductAsSold(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setPurchasedBy("buyer-id");
        productRepository.save(product);

        return ResponseEntity.ok().body(Map.of("message", "Product marked as sold"));
    }

}