package be.helha.assurappapi.authentification.repositories;

import be.helha.assurappapi.authentification.models.Insurer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsurerRepository extends JpaRepository<Insurer, Long> {
}
