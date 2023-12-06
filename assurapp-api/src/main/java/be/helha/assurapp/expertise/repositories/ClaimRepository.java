package be.helha.assurapp.expertise.repositories;

import be.helha.assurapp.expertise.models.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClaimRepository extends JpaRepository<Claim, Long>{
}
