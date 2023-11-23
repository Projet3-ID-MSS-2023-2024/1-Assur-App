package be.helha.assurapp.insurance.services;

import be.helha.assurapp.insurance.models.Payment;

import java.util.List;

public interface IPaymentService {

    List<Payment> getAll();
    Payment getOne(Long id);
    Payment add(Payment payment);
    Payment update(Payment payment);
    void delete(Long id);
    
}
