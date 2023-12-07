package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.insurance.models.Payment;
import be.helha.assurapp.insurance.services.IPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private IPaymentService service;

    @GetMapping
    public List<Payment> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Payment getOne(@PathVariable Long id) {
        return this.service.getOne(id);
    }

    @PostMapping
    public Payment add(Payment payment) {
        return this.service.add(payment);
    }

    @PutMapping
    public Payment update(Payment payment) {
        return this.service.update(payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

}
