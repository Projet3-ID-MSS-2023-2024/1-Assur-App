package be.helha.assurappapi.expertise.services;

import be.helha.assurappapi.expertise.models.Claim;

import java.util.List;

public interface IClaimService {

    List<Claim> findAll();
    Claim findById(Long id);
    Claim save(Claim claim);
    Claim update(Claim claim);
    void deleteById(Long id);

}
