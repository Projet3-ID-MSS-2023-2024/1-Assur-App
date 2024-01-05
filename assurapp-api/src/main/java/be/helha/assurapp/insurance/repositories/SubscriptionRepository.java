package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s FROM Subscription s WHERE s.client.id = :id")
    List<Subscription> findSubscriptionsByClient(@Param("id") Long id);

    @Query("SELECT s FROM Subscription s WHERE s.insurance.insurer.id = :id")
    List<Subscription> findSubscriptionsByInsurer(@Param("id") Long id);

    Subscription findByPaymentsId(@Param("id") Long id);
}
