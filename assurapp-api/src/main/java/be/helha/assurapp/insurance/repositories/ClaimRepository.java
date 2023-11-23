package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.insurance.models.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}
