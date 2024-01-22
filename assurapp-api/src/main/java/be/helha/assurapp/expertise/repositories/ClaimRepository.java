package be.helha.assurapp.expertise.repositories;

import be.helha.assurapp.expertise.models.Claim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long>{

    // query to find claim by expert id
    @Query("SELECT c FROM Claim c WHERE c.expert.id = :id ORDER BY c.id ASC")
    List<Claim> findClaimByExpert(@Param("id") Long id);

    // query to find claim by client id
    @Query("SELECT c FROM Claim c WHERE c.client.id = :id ORDER BY c.id ASC")
    List<Claim> findClaimByClient(@Param("id") Long id);

    // find claims by insurer id
    @Query("SELECT c FROM Claim c WHERE c.client.id IN (SELECT s.client.id FROM Subscription s WHERE s.insurance.id IN (SELECT i.id FROM Insurance i WHERE i.insurer.id = :id))ORDER BY c.id ASC")
    List<Claim> findClaimByInsurer(@Param("id") Long id);


    // find claims by insurer id
    @Query("SELECT c FROM Claim c WHERE c.client.id IN (SELECT s.client.id FROM Subscription s WHERE s.insurance.id IN (SELECT i.id FROM Insurance i WHERE i.insurer.id = :id)) AND c.status = 'PENDING'")
    List<Claim> findPendingClaimByInsurer(@Param("id") Long id);

    //find claims by status
    @Query("SELECT c FROM Claim c WHERE c.status = :status")
    List<Claim> findClaimByStatus(@Param("status") String status);


}
