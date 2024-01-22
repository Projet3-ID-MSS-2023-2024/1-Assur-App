package be.helha.assurapp.expertise.controllers;


import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.models.Expertise;
import be.helha.assurapp.expertise.services.IClaimService;
import be.helha.assurapp.expertise.services.IExpertiseService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
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



    @GetMapping("/expert/{id}")
    public List<Expertise> findExpertiseByExpert(@PathVariable Long id) {
        return expertiseService.findExpertiseByExpert(id);
    }

    @GetMapping("/insurer/{id}")
    public List<Expertise>findExpertiseByInsurer(@PathVariable Long id){
    	return expertiseService.findExpertiseByInsurer(id);
    }

    @PostMapping
    public Expertise save(@RequestBody Expertise expertise) {
        return expertiseService.save(expertise);
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
