package be.helha.assurappapi.authentification.repositories;

import be.helha.assurappapi.authentification.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
