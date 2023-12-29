package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.models.Term;
import be.helha.assurapp.insurance.services.IInsuranceService;
import be.helha.assurapp.insurance.services.ITermService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/insurances")
public class InsuranceController {

    private IInsuranceService service;
    private ITermService termService;

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
        List<Term> terms = new ArrayList<>();
        for (Term term:insurance.getTerms()) {
            terms.add(this.termService.add(term));
        }
        insurance.setTerms(terms);
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
