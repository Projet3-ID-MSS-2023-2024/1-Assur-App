package be.helha.assurapp.expertise.services.impl;

import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.repositories.ClaimRepository;
import be.helha.assurapp.expertise.services.IClaimService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
@AllArgsConstructor
public class ClaimServiceImpl implements IClaimService {

    private ClaimRepository claimRepository;

    @Override
    public List<Claim> findAll() {
        return claimRepository.findAll();
    }

    @Override
    public Claim findById(Long id) {
        return claimRepository.findById(id).orElse(null);
    }

    @Override
    public Claim save(Claim claim) {
        return claimRepository.save(claim);
    }

    @Override
    public void deleteById(Long id) {
        claimRepository.deleteById(id);
    }

    @Override
    public Claim update(Claim claim) {
        return claimRepository.save(claim);
    }
}
