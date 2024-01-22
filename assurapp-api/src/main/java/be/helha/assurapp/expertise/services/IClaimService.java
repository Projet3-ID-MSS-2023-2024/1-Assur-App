package be.helha.assurapp.expertise.services;

import be.helha.assurapp.expertise.models.Claim;

import java.util.List;

public interface IClaimService {

    List<Claim> findAll();

    List<Claim> findClaimByExpert(Long id);
    List<Claim> findClaimByClient(Long id);
    List<Claim> findClaimByInsurer(Long id);
    List<Claim> findPendingClaimByInsurer(Long id);

    List<Claim> findClaimByStatus(String status);

    Claim findById(Long id);

    Claim save(Claim claim);
    Claim update(Claim claim);
    void deleteById(Long id);

}
