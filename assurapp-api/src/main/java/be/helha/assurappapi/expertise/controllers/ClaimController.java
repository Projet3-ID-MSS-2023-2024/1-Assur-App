package be.helha.assurappapi.expertise.controllers;

import be.helha.assurappapi.expertise.models.Claim;
import be.helha.assurappapi.expertise.services.IClaimService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/claims")
public class ClaimController {

    private IClaimService claimService;

    @GetMapping
    public List<Claim> findAll() {
        return claimService.findAll();
    }

    @GetMapping("/{id}")
    public Claim findById(Long id) {
        return claimService.findById(id);
    }

    @PostMapping
    public Claim save(Claim claim) {
        return claimService.save(claim);
    }

    @PutMapping
    public Claim update(Claim claim) {
        return claimService.update(claim);
    }

    @DeleteMapping("/{id}")
    public void deleteById(Long id) {
        claimService.deleteById(id);
    }

}
