package be.helha.assurapp.insurance.services;

import be.helha.assurapp.insurance.models.Subscription;

import java.util.List;

public interface ISubscriptionService {

    List<Subscription> getAll();
    Subscription getOne(Long id);
    Subscription add(Subscription subscription);
    Subscription update(Subscription subscription);
    void delete(Long id);

}
