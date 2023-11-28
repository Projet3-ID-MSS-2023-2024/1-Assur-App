package be.helha.assurappapi.expertise.services.impl;

import be.helha.assurappapi.expertise.models.Expertise;
import be.helha.assurappapi.expertise.repositories.ExpertiseRepository;
import be.helha.assurappapi.expertise.services.IExpertiseService;

import java.util.List;

public class ExpertiseServiceImpl implements IExpertiseService {

    private ExpertiseRepository expertiseRepository;

    @Override
    public List<Expertise> findAll() {
        return expertiseRepository.findAll();
    }

    @Override
    public Expertise findById(Long id) {
        return expertiseRepository.findById(id).orElse(null);
    }

    @Override
    public Expertise save(Expertise expertise) {
        return expertiseRepository.save(expertise);
    }

    @Override
    public void deleteById(Long id) {
        expertiseRepository.deleteById(id);
    }

    @Override
    public Expertise update(Expertise expertise) {
        return expertiseRepository.save(expertise);
    }


}
