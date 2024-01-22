package be.helha.assurapp.expertise.services.impl;

import be.helha.assurapp.expertise.models.Expertise;
import be.helha.assurapp.expertise.repositories.ExpertiseRepository;
import be.helha.assurapp.expertise.services.IExpertiseService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
@AllArgsConstructor
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
    public List<Expertise> findExpertiseByExpert(Long id) {
        return expertiseRepository.findExpertiseByExpert(id);
    }

    @Override
    public List<Expertise> findExpertiseByInsurer(Long id) {
        return expertiseRepository.findExpertiseByInsurer(id);
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
