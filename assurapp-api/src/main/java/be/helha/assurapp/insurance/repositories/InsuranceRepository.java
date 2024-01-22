package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    List<Insurance> findByInsurer(User insurer);
    List<Insurance> findByType(InsuranceType type);

    @Query("SELECT i FROM Insurance i WHERE i.id in (SELECT s.insurance.id FROM Subscription s WHERE s.client.id = :id AND s.endDate > CURRENT DATE)")
    List<Insurance> findInsurancesByClient(@Param("id") Long id);
}
