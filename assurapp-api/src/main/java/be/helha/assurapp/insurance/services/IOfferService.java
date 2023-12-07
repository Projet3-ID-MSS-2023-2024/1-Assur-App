package be.helha.assurapp.insurance.services;

import be.helha.assurapp.insurance.models.Offer;

import java.util.List;

public interface IOfferService {

    List<Offer> getAll();
    Offer getOne(Long id);
    Offer add(Offer offer);
    Offer update(Offer offer);
    void delete(Long id);

}
