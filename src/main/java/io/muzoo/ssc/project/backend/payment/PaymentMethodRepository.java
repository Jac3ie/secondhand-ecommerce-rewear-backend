package io.muzoo.ssc.project.backend.payment;

import io.muzoo.ssc.project.backend.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
    Optional<PaymentMethod> findByUser(User user);
}