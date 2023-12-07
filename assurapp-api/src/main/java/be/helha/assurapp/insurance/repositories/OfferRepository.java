package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.insurance.models.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
