package be.helha.assurapp.expertise.controllers;

import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.services.IClaimService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Date;
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

    @GetMapping("/user/{id}")
    public List<Claim> findByUserId(@PathVariable Long id) {
        return claimService.findClaimByClient(id);
    }
    @GetMapping("/expert/{id}")
    public List<Claim> findByExpertId(@PathVariable Long id) {
        return claimService.findClaimByExpert(id);
    }

    @GetMapping("/insurer/{id}")
    public List<Claim> findByInsurerId(@PathVariable Long id) {
        return claimService.findClaimByInsurer(id);
    }

    @GetMapping("/insurer/{id}/pending")
    public List<Claim> findPendingByInsurerId(@PathVariable Long id) {
        return claimService.findPendingClaimByInsurer(id);
    }

    @GetMapping("/status/{status}")
    public List<Claim> findByStatus(@PathVariable String status) {
        return claimService.findClaimByStatus(status);
    }

    @GetMapping("/{id}")
    public Claim findById(@PathVariable Long id) {
        return claimService.findById(id);
    }

    @PostMapping
    public Claim save(@RequestBody Claim claim){
       return claimService.save(claim);
    }


    @PutMapping
    public Claim update(@RequestBody Claim claim) {
        return claimService.update(claim);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        claimService.deleteById(id);
    }

}
