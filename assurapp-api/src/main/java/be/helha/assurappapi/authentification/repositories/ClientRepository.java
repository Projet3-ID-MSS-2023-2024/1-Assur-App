package be.helha.assurappapi.authentification.repositories;

import be.helha.assurappapi.authentification.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client, Long> {
}
