package be.helha.assurapp.insurance.services.impl;

import be.helha.assurapp.insurance.models.Claim;
import be.helha.assurapp.insurance.repositories.ClaimRepository;
import be.helha.assurapp.insurance.services.IClaimService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@AllArgsConstructor
public class ClaimServiceImpl implements IClaimService {

    private ClaimRepository repository;

    @Override
    public List<Claim> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Claim getOne(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public Claim add(Claim claim) {
        return this.repository.save(claim);
    }

    @Override
    public Claim update(Claim claim) {
        return this.repository.save(claim);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
