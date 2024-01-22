package be.helha.assurapp.insurance.services.impl;

import be.helha.assurapp.insurance.models.Payment;
import be.helha.assurapp.insurance.repositories.PaymentRepository;
import be.helha.assurapp.insurance.services.IPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PaymentServiceImpl implements IPaymentService {

    private PaymentRepository repository;

    @Override
    public List<Payment> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Payment getOne(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public List<Payment> getByClient(Long id) {
        return this.repository.findPaymentsByClient(id);
    }

    @Override
    public List<Payment> getByInsurer(Long id) {
        return this.repository.findPaymentsByInsurer(id);
    }

    @Override
    public Payment add(Payment payment) {
        return this.repository.save(payment);
    }

    @Override
    public Payment update(Payment payment) {
        return this.repository.save(payment);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
