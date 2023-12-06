package be.helha.assurapp.expertise.repositories;

import be.helha.assurapp.expertise.models.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {
}
