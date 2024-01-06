package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.insurance.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p IN (SELECT s.payments from Subscription s WHERE s.client.id = :id) ORDER BY p.id ASC")
    List<Payment> findPaymentsByClient(@Param("id") Long id);

    @Query("SELECT p FROM Payment p WHERE p IN (SELECT s.payments from Subscription s WHERE s.insurance.insurer.id = :id) ORDER BY p.id ASC")
    List<Payment> findPaymentsByInsurer(@Param("id") Long id);
}
