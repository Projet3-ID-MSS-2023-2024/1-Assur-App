package be.helha.assurapp.expertise.controllers;


import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.models.Expertise;
import be.helha.assurapp.expertise.services.IClaimService;
import be.helha.assurapp.expertise.services.IExpertiseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/expertises")
public class ExpertiseController {

    private IExpertiseService expertiseService;
    private IClaimService claimService;

    @GetMapping("/{id}")
    public Expertise findById(@PathVariable Long id) {
        return expertiseService.findById(id);
    }

    @GetMapping
    public List<Expertise> findAll() {
        return expertiseService.findAll();
    }

    @PostMapping
    public Long save(@RequestBody Expertise expertise) {
        expertiseService.save(expertise);
        Claim claim = claimService.findById(expertise.getClaim().getId());
        claim.setExpertise(expertise.getId());
        claimService.update(claim);
        return claim.getExpertise();
    }

    @PutMapping
    public Expertise update(Expertise expertise) {
        return expertiseService.update(expertise);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        expertiseService.deleteById(id);
    }



}
