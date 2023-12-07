package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.insurance.models.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {
}
