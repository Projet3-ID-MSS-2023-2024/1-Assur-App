package be.helha.assurapp.insurance.services.impl;

import be.helha.assurapp.insurance.models.Offer;
import be.helha.assurapp.insurance.repositories.OfferRepository;
import be.helha.assurapp.insurance.services.IOfferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OfferServiceImpl implements IOfferService {

    private OfferRepository repository;

    @Override
    public List<Offer> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Offer getOne(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public Offer add(Offer offer) {
        return this.repository.save(offer);
    }

    @Override
    public Offer update(Offer offer) {
        return this.repository.save(offer);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
