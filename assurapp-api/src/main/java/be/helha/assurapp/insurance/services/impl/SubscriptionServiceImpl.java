package be.helha.assurapp.insurance.services.impl;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.models.Subscription;
import be.helha.assurapp.insurance.repositories.SubscriptionRepository;
import be.helha.assurapp.insurance.services.ISubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class SubscriptionServiceImpl implements ISubscriptionService {

    private SubscriptionRepository repository;

    @Override
    public List<Subscription> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Subscription getOne(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public List<Subscription> getByClient(Long id) {
        return this.repository.findSubscriptionsByClient(id);
    }

    @Override
    public List<Subscription> getByInsurer(Long id) {
        return this.repository.findSubscriptionsByInsurer(id);
    }

    @Override
    public Subscription add(Subscription subscription) {
        return this.repository.save(subscription);
    }

    @Override
    public Subscription update(Subscription subscription) {
        return this.repository.save(subscription);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
