package be.helha.assurapp.insurance.services;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;

import java.util.List;

public interface IInsuranceService {

    List<Insurance> getAll();
    List<Insurance> getByType(InsuranceType type);
    List<Insurance> getByClient(Long id);
    List<Insurance> getByInsurer(User insurer);
    Insurance getOne(Long id);
    Insurance add(Insurance offer);
    Insurance update(Insurance offer);
    void delete(Long id);

}
