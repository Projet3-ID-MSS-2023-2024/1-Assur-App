package be.helha.assurappapi.expertise.controllers;


import be.helha.assurappapi.expertise.models.Expertise;
import be.helha.assurappapi.expertise.services.IExpertiseService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/expertises")
public class ExpertiseController {

    private IExpertiseService expertiseService;

    @GetMapping("/{id}")
    public Expertise findById(Long id) {
        return expertiseService.findById(id);
    }

    @GetMapping
    public List<Expertise> findAll() {
        return expertiseService.findAll();
    }

    @PostMapping
    public Expertise save(Expertise expertise) {
        return expertiseService.save(expertise);
    }

    @PutMapping
    public Expertise update(Expertise expertise) {
        return expertiseService.update(expertise);
    }

    @DeleteMapping("/{id}")
    public void deleteById(Long id) {
        expertiseService.deleteById(id);
    }



}
