package be.helha.assurapp.insurance.services;

import be.helha.assurapp.insurance.models.Claim;

import java.util.List;

public interface IClaimService {

    List<Claim> getAll();
    Claim getOne(Long id);
    Claim add(Claim claim);
    Claim update(Claim claim);
    void delete(Long id);

}
