package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.insurance.models.Claim;
import be.helha.assurapp.insurance.services.IClaimService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/claims")
public class ClaimController {

    private IClaimService service;

    @GetMapping
    public List<Claim> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Claim getOne(@PathVariable Long id) {
        return this.service.getOne(id);
    }

    @PostMapping
    public Claim add(Claim claim) {
        return this.service.add(claim);
    }

    @PutMapping
    public Claim update(Claim claim) {
        return this.service.update(claim);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

}
