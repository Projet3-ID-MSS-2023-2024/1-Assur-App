package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.services.IInsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/insurances")
public class InsuranceController {

    private IInsuranceService service;

    @GetMapping
    public List<Insurance> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Insurance getOne(@PathVariable Long id) {
        return this.service.getOne(id);
    }

    @PostMapping
    public Insurance add(@RequestBody Insurance insurance) {
        return this.service.add(insurance);
    }

    @PutMapping
    public Insurance update(@RequestBody Insurance insurance) {
        return this.service.update(insurance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

}
