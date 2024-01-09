package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.insurance.models.Payment;
import be.helha.assurapp.insurance.models.Subscription;
import be.helha.assurapp.insurance.services.IPaymentService;
import be.helha.assurapp.insurance.services.ISubscriptionService;
import be.helha.assurapp.utils.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private IPaymentService service;
    private ISubscriptionService subscriptionService;
    private EmailSender emailSender;

    @GetMapping
    public List<Payment> getAll() {
        return this.service.getAll();
    }

    @GetMapping("/{id}")
    public Payment getOne(@PathVariable Long id) {
        return this.service.getOne(id);
    }

    @GetMapping("/client/{id}")
    public List<Payment> getByClient(@PathVariable Long id) {
        return this.service.getByClient(id);
    }

    @GetMapping("/insurer/{id}")
    public List<Payment> getByInsurer(@PathVariable Long id) {
        return this.service.getByInsurer(id);
    }

    @PostMapping("/notify")
    public Payment notify(@RequestBody Payment payment) {
        Payment p = this.service.getOne(payment.getId());
        Subscription s = this.subscriptionService.getByPayment(p.getId());
        String message = String.format("""
                Dear %s,
                                
                We hope this message finds you well.
                This is a friendly reminder that your upcoming insurance premium payment is due soon.
                                
                Policy Details:
                - Policy Name: %s
                - Policy Type: %s
                - Payment Due Date: %s
                - Amount Due: %s €
                                
                To ensure uninterrupted coverage, please make your payment by the specified due date.
                You can conveniently make the payment through one of the following methods:
                                
                If you've already made the payment, kindly disregard this email.
                We appreciate your prompt attention to this matter and thank you for choosing %s for your insurance needs.
                                
                Should you have any questions or require assistance, feel free to contact our customer service team at %s.
                                
                Thank you for your continued trust.
                                
                Best regards,
                %s
                """,
                s.getClient().getName() + ' ' + s.getClient().getLastname().toUpperCase(),
                s.getInsurance().getName(),
                s.getInsurance().getType(),
                p.getTransactionDate(),
                p.getAmount(),
                s.getInsurance().getInsurer().getName() + ' ' + s.getInsurance().getInsurer().getLastname(),
                s.getInsurance().getInsurer().getEmail(),
                s.getInsurance().getInsurer().getName() + ' ' + s.getInsurance().getInsurer().getLastname());

        this.emailSender.send(s.getInsurance().getInsurer().getEmail(), s.getClient().getEmail(), "Upcoming Insurance Payment Deadline", message);
        return p;
    }

    @PostMapping
    public Payment add(@RequestBody Payment payment) {
        return this.service.add(payment);
    }

    @PutMapping
    public Payment update(@RequestBody Payment payment) {
        return this.service.update(payment);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.service.delete(id);
    }

}
