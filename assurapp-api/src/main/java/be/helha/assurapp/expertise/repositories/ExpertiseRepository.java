package be.helha.assurapp.expertise.repositories;

import be.helha.assurapp.expertise.models.Expertise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpertiseRepository extends JpaRepository<Expertise, Long> {

    //query to find expertise by expert id
    @Query("SELECT e FROM Expertise e WHERE e.expert.id = :id ORDER BY e.id ASC")
    List<Expertise> findExpertiseByExpert(@Param("id") Long id);

    //query to find expertise by insurer id
    @Query("SELECT e FROM Expertise e WHERE e.claim.client.id IN (SELECT s.client.id FROM Subscription s WHERE s.insurance.id IN (SELECT i.id FROM Insurance i WHERE i.insurer.id = :id)) ORDER BY e.id ASC")
    List<Expertise> findExpertiseByInsurer(@Param("id") Long id);


}
