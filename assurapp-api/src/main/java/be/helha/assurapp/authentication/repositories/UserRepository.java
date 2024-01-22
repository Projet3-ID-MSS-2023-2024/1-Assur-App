package be.helha.assurapp.authentication.repositories;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id IN (SELECT s.client.id FROM Subscription s WHERE s.insurance.id IN (SELECT i.id FROM Insurance i WHERE i.insurer.id = :id))")
    List<User> findUsersByInsurer(@Param("id") Long id);

    @Query("SELECT s FROM Subscription s WHERE s.client.id = :id and s.endDate > current date ")
    List<Subscription> findInsuranceByUser(@Param("id") Long id);

}
