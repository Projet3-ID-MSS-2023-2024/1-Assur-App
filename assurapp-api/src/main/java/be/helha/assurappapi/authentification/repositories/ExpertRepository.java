package be.helha.assurappapi.authentification.repositories;

import be.helha.assurappapi.authentification.models.Expert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpertRepository extends JpaRepository<Expert, Long> {
}
