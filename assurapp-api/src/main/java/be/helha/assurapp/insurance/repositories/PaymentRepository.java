package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.insurance.models.Payment;
import be.helha.assurapp.insurance.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.subscription.client.id = :id")
    List<Payment> findPaymentsByClient(@Param("id") Long id);

    @Query("SELECT p FROM Payment p WHERE p.subscription.insurance.insurer.id = :id")
    List<Payment> findPaymentsByInsurer(@Param("id") Long id);
}
