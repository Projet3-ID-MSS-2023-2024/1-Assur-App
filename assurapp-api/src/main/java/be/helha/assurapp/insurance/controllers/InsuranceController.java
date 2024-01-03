package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.models.Term;
import be.helha.assurapp.insurance.services.IInsuranceService;
import be.helha.assurapp.insurance.services.ITermService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/insurances")
public class InsuranceController {

    private UserRepository repository;
    private IInsuranceService service;
    private ITermService termService;

    @GetMapping
    public List<Insurance> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id:\\d+}")
    public Insurance getOne(@PathVariable Long id) {
        return this.service.getOne(id);
    }

    @GetMapping("/{email:.+@.+\\..+}")
    public List<Insurance> getByInsurer(@PathVariable String email) {
        Optional<User> user = this.repository.findByEmail(email);
        if (user.isEmpty()) return Collections.emptyList();
        return this.service.getByInsurer(user.get());
    }

    @GetMapping("/client/{id}")
    public List<Insurance> getByClient(@PathVariable Long id) {
        return this.service.getByClient(id);
    }

    @PostMapping
    public Insurance add(@RequestBody Insurance insurance) {
        List<Term> terms = new ArrayList<>();
        for (Term term:insurance.getTerms()) {
            terms.add(this.termService.add(term));
        }
        insurance.setTerms(terms);
        insurance.setInsurer(repository.findById(insurance.getInsurer().getId()).get());
        return this.service.add(insurance);
    }

    @PutMapping
    public Insurance update(@RequestBody Insurance insurance) {
        for (Term term: insurance.getTerms()) this.termService.update(term);
        return this.service.update(insurance);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        for (Term term: this.service.getOne(id).getTerms()) this.termService.delete(term.getId());
        this.service.delete(id);
    }

}
