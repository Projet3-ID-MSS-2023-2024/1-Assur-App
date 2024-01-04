package be.helha.assurapp.insurance.services.impl;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.repositories.InsuranceRepository;
import be.helha.assurapp.insurance.services.IInsuranceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class InsuranceServiceImpl implements IInsuranceService {

    private InsuranceRepository repository;

    @Override
    public List<Insurance> getAll() {
        return this.repository.findAll();
    }

    @Override
    public List<Insurance> getByType(InsuranceType type) {
        return this.repository.findByType(type);
    }

    @Override
    public List<Insurance> getByInsurer(User insurer) {
        return this.repository.findByInsurer(insurer);
    }

    @Override
    public Insurance getOne(Long id) {
        return this.repository.findById(id).orElseThrow();
    }

    @Override
    public Insurance add(Insurance insurance) {
        return this.repository.save(insurance);
    }

    @Override
    public Insurance update(Insurance insurance) {
        return this.repository.save(insurance);
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }
}
