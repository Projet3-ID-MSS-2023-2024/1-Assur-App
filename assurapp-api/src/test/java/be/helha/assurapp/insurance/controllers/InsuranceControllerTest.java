package be.helha.assurapp.insurance.controllers;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.authentication.services.UserService;
import be.helha.assurapp.insurance.enums.InsuranceType;
import be.helha.assurapp.insurance.models.Insurance;
import be.helha.assurapp.insurance.models.Term;
import be.helha.assurapp.insurance.services.IInsuranceService;
import be.helha.assurapp.insurance.services.ITermService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class InsuranceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private IInsuranceService insuranceService;

    @MockBean
    private ITermService termService;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleRepository roleRepository;

    @InjectMocks
    private InsuranceController insuranceController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(insuranceController).build();
    }

    @Test
    @WithMockUser
    void getAllInsurancesSuccess() throws Exception {
        List<Insurance> insurances = Arrays.asList(
                new Insurance(1L, "HEALTH", InsuranceType.HEALTH, 1000.00, new User(), Collections.emptyList()),
                new Insurance(2L, "CAR", InsuranceType.CAR, 1000.00, new User(), Collections.emptyList()));

        when(insuranceService.getAll()).thenReturn(insurances);

        mockMvc.perform(get("/api/v1/insurances"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(insurances.size()));

        verify(insuranceService, times(1)).getAll();
    }

    @Test
    @WithMockUser
    void getOneInsuranceSuccess() throws Exception {
        Long id = 1L;
        Insurance insurance = new Insurance(1L, "HEALTH", InsuranceType.HEALTH, 1000.00, new User(), Collections.emptyList());

        when(insuranceService.getOne(id)).thenReturn(insurance);

        mockMvc.perform(get("/api/v1/insurances/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(insuranceService, times(1)).getOne(id);
    }

    @Test
    @WithMockUser
    void getByInsurerSuccess() throws Exception {
        String email = "user@example.com";
        User user = new User();
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(insuranceService.getByInsurer(user)).thenReturn(insurances);

        mockMvc.perform(get("/api/v1/insurances/{email:.+@.+\\..+}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(insurances.size()));

        verify(userRepository, times(1)).findByEmail(email);
        verify(insuranceService, times(1)).getByInsurer(user);
    }

    @Test
    @WithMockUser
    void getByInsurerNotFound() throws Exception {
        String email = "nonexistent@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/insurances/{email:.+@.+\\..+}", email))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(userRepository, times(1)).findByEmail(email);
        verify(insuranceService, never()).getByInsurer(any(User.class));
    }

    @Test
    @WithMockUser
    void getByClientSuccess() throws Exception {
        Long clientId = 1L;
        List<Insurance> insurances = Arrays.asList(new Insurance(), new Insurance());

        when(insuranceService.getByClient(clientId)).thenReturn(insurances);

        mockMvc.perform(get("/api/v1/insurances/client/{id}", clientId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(insurances.size()));

        verify(insuranceService, times(1)).getByClient(clientId);
    }

    private String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
