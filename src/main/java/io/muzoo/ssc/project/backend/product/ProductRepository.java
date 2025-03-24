package io.muzoo.ssc.project.backend.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //this interface just to extend JpaRepo to get CRUD operations
    //with this, when we call instance of ProductRepository
    //we can do .save() to create a product, also:
    //.findById(Long id)
    //.findAll()
    //.deleteById(Long id)
    //.count()
    List<Product> findByName(String name, String description);
}