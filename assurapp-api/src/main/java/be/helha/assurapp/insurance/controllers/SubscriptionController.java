package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.models.Subscription;
import be.helha.assurapp.insurance.services.ISubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/subscriptions")
public class SubscriptionController {

    ISubscriptionService service;

    @GetMapping
    public List<Subscription> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Subscription getOne(@PathVariable Long id) {
        return this.service.getOne(id);
    }

    @GetMapping("/client/{id}")
    public List<Subscription> getByClient(@PathVariable Long id) {
        return this.service.getByClient(id);
    }

    @GetMapping("/insurer/{id}")
    public List<Subscription> getByInsurer(@PathVariable Long id) {
        return this.service.getByInsurer(id);
    }

    @GetMapping("/payment/{id}")
    public Subscription getByPayment(@PathVariable Long id) {
        return this.service.getByPayment(id);
    }

    @PostMapping
    public Subscription add(@RequestBody Subscription subscription) {
        return this.service.add(subscription);
    }

    @PutMapping
    public Subscription update(@RequestBody Subscription subscription) {
        return this.service.update(subscription);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

}
