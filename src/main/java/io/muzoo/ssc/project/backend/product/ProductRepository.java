package io.muzoo.ssc.project.backend.product;

import io.muzoo.ssc.project.backend.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    //this interface just to extend JpaRepo to get CRUD operations
    //with this, when we call instance of ProductRepository
    //we can do .save() to create a product, also:
    //.findById(Long id)
    //.findAll()
    //.deleteById(Long id)
    //.count()
    List<Product> searchByName(@Param("query") String query);
    List<Product> findBySoldAtIsNotNull();

    List<Product> findByPurchasedBy(User user);


}