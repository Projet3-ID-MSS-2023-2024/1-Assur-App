package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.insurance.models.Offer;
import be.helha.assurapp.insurance.services.IOfferService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/offers")
public class OfferController {

    private IOfferService service;

    @GetMapping
    public List<Offer> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Offer getOne(@PathVariable Long id) {
        return this.service.getOne(id);
    }

    @PostMapping
    public Offer add(Offer offer) {
        return this.service.add(offer);
    }

    @PutMapping
    public Offer update(Offer offer) {
        return this.service.update(offer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

}
