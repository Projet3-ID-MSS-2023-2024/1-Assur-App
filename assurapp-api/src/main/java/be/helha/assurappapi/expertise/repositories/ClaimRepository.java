package be.helha.assurappapi.expertise.repositories;

import be.helha.assurappapi.expertise.models.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClaimRepository extends JpaRepository<Claim, Long>{
}
