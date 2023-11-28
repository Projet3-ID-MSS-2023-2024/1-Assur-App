package be.helha.assurappapi.expertise.repositories;

import be.helha.assurappapi.expertise.models.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {
}
