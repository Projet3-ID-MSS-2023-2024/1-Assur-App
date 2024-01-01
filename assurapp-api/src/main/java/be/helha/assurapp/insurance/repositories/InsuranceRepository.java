package be.helha.assurapp.insurance.repositories;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    List<Insurance> findByInsurer(User insurer);
    List<Insurance> findByType(InsuranceType type);
}
