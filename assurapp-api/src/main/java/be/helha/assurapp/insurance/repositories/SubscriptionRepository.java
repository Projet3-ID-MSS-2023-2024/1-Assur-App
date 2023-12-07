package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.insurance.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
