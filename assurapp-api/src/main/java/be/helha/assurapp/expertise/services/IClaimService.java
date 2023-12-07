package be.helha.assurapp.expertise.services;

import be.helha.assurapp.expertise.models.Claim;

import java.util.List;

public interface IClaimService {

    List<Claim> findAll();
    Claim findById(Long id);
    Claim save(Claim claim);
    Claim update(Claim claim);
    void deleteById(Long id);

}
