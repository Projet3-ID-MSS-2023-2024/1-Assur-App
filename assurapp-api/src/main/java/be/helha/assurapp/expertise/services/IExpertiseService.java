package be.helha.assurapp.expertise.services;

import be.helha.assurapp.expertise.models.Expertise;

import java.util.List;

public interface IExpertiseService {

    List<Expertise> findAll();
    Expertise findById(Long id);

    List<Expertise> findExpertiseByExpert(Long id);
    Expertise save(Expertise expertise);
    Expertise update(Expertise expertise);
    void deleteById(Long id);
}
