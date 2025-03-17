package io.muzoo.ssc.project.backend.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //this interface just to extend JpaRepo to get CRUD operations
    //with this, when we call instance of ProductRepository
    //we can do .save() to create a product, also:
    //.findById(Long id)
    //.findAll()
    //.deleteById(Long id)
    //.count()
}
