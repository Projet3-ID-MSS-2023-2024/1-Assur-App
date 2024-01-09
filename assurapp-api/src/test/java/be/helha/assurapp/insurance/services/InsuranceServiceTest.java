package be.helha.assurapp.insurance.services;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.repositories.InsuranceRepository;
import be.helha.assurapp.insurance.services.impl.InsuranceServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class InsuranceServiceTest {

    @Mock
    private InsuranceRepository repository;

    @InjectMocks
    private InsuranceServiceImpl insuranceService;

    @Test
    void getAllInsurancesSuccess() {
        List<Insurance> insurances = Arrays.asList(
                new Insurance(1L, "Health", InsuranceType.HEALTH, 1000.00, new User(), Collections.emptyList()),
                new Insurance(2L, "Home", InsuranceType.HOME, 2000.00, new User(), Collections.emptyList()));
        when(repository.findAll()).thenReturn(insurances);

        List<Insurance> result = insuranceService.getAll();

        assertEquals(insurances, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void getByTypeSuccess() {
        InsuranceType type = InsuranceType.HEALTH;
        List<Insurance> insurances = Arrays.asList(
                new Insurance(1L, "Health", InsuranceType.HEALTH, 1000.00, new User(), Collections.emptyList()),
                new Insurance(2L, "Home", InsuranceType.HEALTH, 2000.00, new User(), Collections.emptyList()));
        when(repository.findByType(type)).thenReturn(insurances);

        List<Insurance> result = insuranceService.getByType(type);

        assertEquals(insurances, result);
        verify(repository, times(1)).findByType(type);
    }

    @Test
    void getByClientSuccess() {
        Long clientId = 1L;
        List<Insurance> insurances = Arrays.asList(
                new Insurance(1L, "Health", InsuranceType.HEALTH, 1000.00, null, Collections.emptyList()),
                new Insurance(2L, "Home", InsuranceType.HOME, 2000.00, null, Collections.emptyList()));
        when(repository.findInsurancesByClient(clientId)).thenReturn(insurances);

        List<Insurance> result = insuranceService.getByClient(clientId);

        assertEquals(insurances, result);
        verify(repository, times(1)).findInsurancesByClient(clientId);
    }

    @Test
    void getByInsurerSuccess() {
        User insurer = new User();
        List<Insurance> insurances = Arrays.asList(
                new Insurance(1L, "Health", InsuranceType.HEALTH, 1000.00, null, Collections.emptyList()),
                new Insurance(2L, "Home", InsuranceType.HOME, 2000.00, null, Collections.emptyList()));
        when(repository.findByInsurer(insurer)).thenReturn(insurances);

        List<Insurance> result = insuranceService.getByInsurer(insurer);

        assertEquals(insurances, result);
        verify(repository, times(1)).findByInsurer(insurer);
    }

    @Test
    void getOneInsuranceSuccess() {
        Long id = 1L;
        Insurance insurance = new Insurance(1L, "Health", InsuranceType.HEALTH, 1000.00, null, Collections.emptyList());
        when(repository.findById(id)).thenReturn(Optional.of(insurance));

        Insurance result = insuranceService.getOne(id);

        assertEquals(insurance, result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void addInsuranceSuccess() {
        Insurance insurance = new Insurance(1L, "Health", InsuranceType.HEALTH, 1000.00, new User(), Collections.emptyList());
        when(repository.save(insurance)).thenReturn(insurance);

        Insurance result = insuranceService.add(insurance);

        assertEquals(insurance, result);
        verify(repository, times(1)).save(insurance);
    }

    @Test
    void updateInsuranceSuccess() {
        Insurance insurance = new Insurance(1L, "Health", InsuranceType.HEALTH, 1000.00, new User(), Collections.emptyList());
        when(repository.save(insurance)).thenReturn(insurance);

        Insurance result = insuranceService.update(insurance);

        assertEquals(insurance, result);
        verify(repository, times(1)).save(insurance);
    }

    @Test
    void deleteInsuranceSuccess() {
        Long id = 1L;
        Insurance insurance = new Insurance(id, "Health", InsuranceType.HEALTH, 1000.00, new User(), Collections.emptyList());
        when(repository.save(insurance)).thenReturn(insurance);

        insuranceService.delete(id);

        verify(repository, times(1)).deleteById(id);
    }

}
