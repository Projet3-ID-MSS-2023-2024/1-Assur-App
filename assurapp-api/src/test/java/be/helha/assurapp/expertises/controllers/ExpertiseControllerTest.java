package be.helha.assurapp.expertises.controllers;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.expertise.controllers.ExpertiseController;
import be.helha.assurapp.expertise.enums.ClaimStatus;
import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.models.Expertise;
import be.helha.assurapp.expertise.services.IExpertiseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExpertiseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IExpertiseService expertiseService;

    @InjectMocks
    private ExpertiseController expertiseController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(expertiseController).build();
    }

    @Test
    @WithMockUser
    void findAllExpertises() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        List<Expertise> expertises = new ArrayList<>();

        roles.add(new Role(1L, RoleList.ADMINISTRATOR));
        roles.add(new Role(2L, RoleList.CLIENT));
        roles.add(new Role(3L, RoleList.EXPERT));
        roles.add(new Role(4L, RoleList.INSURER));

        users.add(new User(1L, "Administrator", "Administrator", "admin@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(0), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(2L, "Client", "Client", "client@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(1), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(3L, "Expert", "Expert", "expert@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(2), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(4L, "Guardian", "Lite", "insurer@guardianlite.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(5L, "Voyager", "Protect", "insurer@voyagerprotect.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(6L, "Peak", "Secure", "insurer@peaksecure.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));

        claims.add(new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, users.get(1), users.get(2)));
        claims.add(new Claim(2L, "Crash Citroen C3",  Date.valueOf(LocalDate.now().plusDays(12)), ClaimStatus.REFUSED, users.get(1), users.get(2)));
        claims.add(new Claim(3L, "Crash Peugeut 206",  Date.valueOf(LocalDate.now().plusDays(16)), ClaimStatus.PENDING, users.get(1), null));
        claims.add(new Claim(4L, "Crash Renault",  Date.valueOf(LocalDate.now().plusDays(18)), ClaimStatus.ASSIGNED, users.get(1), users.get(2)));
        claims.add(new Claim(5L, "Crash Mercedes",  Date.valueOf(LocalDate.now().plusDays(20)), ClaimStatus.PROGRESS, users.get(1), users.get(2)));
        claims.add(new Claim(6L, "Crash Audi",  Date.valueOf(LocalDate.now().plusDays(22)), ClaimStatus.CLOSED, users.get(1), users.get(2)));
        claims.add(new Claim(7L, "Crash Skoda",  Date.valueOf(LocalDate.now().plusDays(24)), ClaimStatus.CANCELLED, users.get(1), users.get(2)));

        expertises.add(new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", claims.get(0), users.get(2)));
        expertises.add(new Expertise(2L, "Wheel damage", Date.valueOf(LocalDate.now().plusDays(13)), 1200.00, "assets/expertises/citroen.jpg",claims.get(1) , users.get(2)));
        expertises.add(new Expertise(4L, "Front side", Date.valueOf(LocalDate.now().plusDays(20)), 700.00, "assets/expertises/citroen.jpg",claims.get(4) , users.get(2)));
        expertises.add(new Expertise(5L, "Front side", Date.valueOf(LocalDate.now().plusDays(22)), 700.00, "assets/expertises/peugeot.jpg",claims.get(5) , users.get(2)));
        expertises.add(new Expertise(6L, "Front side", Date.valueOf(LocalDate.now().plusDays(24)), 700.00, "assets/expertises/clio.jpg",claims.get(6) , users.get(2)));

        when(expertiseService.findAll()).thenReturn(expertises);

        mockMvc.perform(get("/api/v1/expertises"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expertises.size()));

        verify(expertiseService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void findExpertiseById() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        List<Expertise> expertises = new ArrayList<>();

        roles.add(new Role(1L, RoleList.ADMINISTRATOR));
        roles.add(new Role(2L, RoleList.CLIENT));
        roles.add(new Role(3L, RoleList.EXPERT));
        roles.add(new Role(4L, RoleList.INSURER));

        users.add(new User(1L, "Administrator", "Administrator", "admin@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(0), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(2L, "Client", "Client", "client@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(1), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(3L, "Expert", "Expert", "expert@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(2), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(4L, "Guardian", "Lite", "insurer@guardianlite.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(5L, "Voyager", "Protect", "insurer@voyagerprotect.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(6L, "Peak", "Secure", "insurer@peaksecure.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));

        claims.add(new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, users.get(1), users.get(2)));
        claims.add(new Claim(2L, "Crash Citroen C3",  Date.valueOf(LocalDate.now().plusDays(12)), ClaimStatus.REFUSED, users.get(1), users.get(2)));
        claims.add(new Claim(3L, "Crash Peugeut 206",  Date.valueOf(LocalDate.now().plusDays(16)), ClaimStatus.PENDING, users.get(1), null));
        claims.add(new Claim(4L, "Crash Renault",  Date.valueOf(LocalDate.now().plusDays(18)), ClaimStatus.ASSIGNED, users.get(1), users.get(2)));
        claims.add(new Claim(5L, "Crash Mercedes",  Date.valueOf(LocalDate.now().plusDays(20)), ClaimStatus.PROGRESS, users.get(1), users.get(2)));
        claims.add(new Claim(6L, "Crash Audi",  Date.valueOf(LocalDate.now().plusDays(22)), ClaimStatus.CLOSED, users.get(1), users.get(2)));
        claims.add(new Claim(7L, "Crash Skoda",  Date.valueOf(LocalDate.now().plusDays(24)), ClaimStatus.CANCELLED, users.get(1), users.get(2)));

        expertises.add(new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", claims.get(0), users.get(2)));
        expertises.add(new Expertise(2L, "Wheel damage", Date.valueOf(LocalDate.now().plusDays(13)), 1200.00, "assets/expertises/citroen.jpg",claims.get(1) , users.get(2)));
        expertises.add(new Expertise(4L, "Front side", Date.valueOf(LocalDate.now().plusDays(20)), 700.00, "assets/expertises/citroen.jpg",claims.get(4) , users.get(2)));
        expertises.add(new Expertise(5L, "Front side", Date.valueOf(LocalDate.now().plusDays(22)), 700.00, "assets/expertises/peugeot.jpg",claims.get(5) , users.get(2)));
        expertises.add(new Expertise(6L, "Front side", Date.valueOf(LocalDate.now().plusDays(24)), 700.00, "assets/expertises/clio.jpg",claims.get(6) , users.get(2)));

        Long id = expertises.get(0).getId();

        when(expertiseService.findById(id)).thenReturn(expertises.get(0));

        mockMvc.perform(get("/api/v1/expertises/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(expertiseService, times(1)).findById(id);
        }

    @Test
    @WithMockUser
    void findExpertiseByExpert() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        List<Expertise> expertises = new ArrayList<>();

        roles.add(new Role(1L, RoleList.ADMINISTRATOR));
        roles.add(new Role(2L, RoleList.CLIENT));
        roles.add(new Role(3L, RoleList.EXPERT));
        roles.add(new Role(4L, RoleList.INSURER));

        users.add(new User(1L, "Administrator", "Administrator", "admin@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(0), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(2L, "Client", "Client", "client@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(1), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(3L, "Expert", "Expert", "expert@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(2), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(4L, "Guardian", "Lite", "insurer@guardianlite.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(5L, "Voyager", "Protect", "insurer@voyagerprotect.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(6L, "Peak", "Secure", "insurer@peaksecure.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));

        claims.add(new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, users.get(1), users.get(2)));
        claims.add(new Claim(2L, "Crash Citroen C3",  Date.valueOf(LocalDate.now().plusDays(12)), ClaimStatus.REFUSED, users.get(1), users.get(2)));
        claims.add(new Claim(3L, "Crash Peugeut 206",  Date.valueOf(LocalDate.now().plusDays(16)), ClaimStatus.PENDING, users.get(1), null));
        claims.add(new Claim(4L, "Crash Renault",  Date.valueOf(LocalDate.now().plusDays(18)), ClaimStatus.ASSIGNED, users.get(1), users.get(2)));
        claims.add(new Claim(5L, "Crash Mercedes",  Date.valueOf(LocalDate.now().plusDays(20)), ClaimStatus.PROGRESS, users.get(1), users.get(2)));
        claims.add(new Claim(6L, "Crash Audi",  Date.valueOf(LocalDate.now().plusDays(22)), ClaimStatus.CLOSED, users.get(1), users.get(2)));
        claims.add(new Claim(7L, "Crash Skoda",  Date.valueOf(LocalDate.now().plusDays(24)), ClaimStatus.CANCELLED, users.get(1), users.get(2)));

        expertises.add(new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", claims.get(0), users.get(2)));
        expertises.add(new Expertise(2L, "Wheel damage", Date.valueOf(LocalDate.now().plusDays(13)), 1200.00, "assets/expertises/citroen.jpg",claims.get(1) , users.get(2)));
        expertises.add(new Expertise(4L, "Front side", Date.valueOf(LocalDate.now().plusDays(20)), 700.00, "assets/expertises/citroen.jpg",claims.get(4) , users.get(2)));
        expertises.add(new Expertise(5L, "Front side", Date.valueOf(LocalDate.now().plusDays(22)), 700.00, "assets/expertises/peugeot.jpg",claims.get(5) , users.get(2)));
        expertises.add(new Expertise(6L, "Front side", Date.valueOf(LocalDate.now().plusDays(24)), 700.00, "assets/expertises/clio.jpg",claims.get(6) , users.get(2)));

        Long id = users.get(2).getId();

        when(expertiseService.findExpertiseByExpert(id)).thenReturn(expertises);

        mockMvc.perform(get("/api/v1/expertises/expert/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expertises.size()));

        verify(expertiseService, times(1)).findExpertiseByExpert(id);
    }

    @Test
    @WithMockUser
    void findExpertiseByInsurer() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        List<Expertise> expertises = new ArrayList<>();

        roles.add(new Role(1L, RoleList.ADMINISTRATOR));
        roles.add(new Role(2L, RoleList.CLIENT));
        roles.add(new Role(3L, RoleList.EXPERT));
        roles.add(new Role(4L, RoleList.INSURER));

        users.add(new User(1L, "Administrator", "Administrator", "admin@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(0), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(2L, "Client", "Client", "client@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(1), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(3L, "Expert", "Expert", "expert@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(2), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(4L, "Guardian", "Lite", "insurer@guardianlite.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(5L, "Voyager", "Protect", "insurer@voyagerprotect.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        users.add(new User(6L, "Peak", "Secure", "insurer@peaksecure.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", roles.get(3), 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));

        claims.add(new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, users.get(1), users.get(2)));
        claims.add(new Claim(2L, "Crash Citroen C3",  Date.valueOf(LocalDate.now().plusDays(12)), ClaimStatus.REFUSED, users.get(1), users.get(2)));
        claims.add(new Claim(3L, "Crash Peugeut 206",  Date.valueOf(LocalDate.now().plusDays(16)), ClaimStatus.PENDING, users.get(1), null));
        claims.add(new Claim(4L, "Crash Renault",  Date.valueOf(LocalDate.now().plusDays(18)), ClaimStatus.ASSIGNED, users.get(1), users.get(2)));
        claims.add(new Claim(5L, "Crash Mercedes",  Date.valueOf(LocalDate.now().plusDays(20)), ClaimStatus.PROGRESS, users.get(1), users.get(2)));
        claims.add(new Claim(6L, "Crash Audi",  Date.valueOf(LocalDate.now().plusDays(22)), ClaimStatus.CLOSED, users.get(1), users.get(2)));
        claims.add(new Claim(7L, "Crash Skoda",  Date.valueOf(LocalDate.now().plusDays(24)), ClaimStatus.CANCELLED, users.get(1), users.get(2)));

        expertises.add(new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", claims.get(0), users.get(2)));
        expertises.add(new Expertise(2L, "Wheel damage", Date.valueOf(LocalDate.now().plusDays(13)), 1200.00, "assets/expertises/citroen.jpg",claims.get(1) , users.get(2)));
        expertises.add(new Expertise(4L, "Front side", Date.valueOf(LocalDate.now().plusDays(20)), 700.00, "assets/expertises/citroen.jpg",claims.get(4) , users.get(2)));
        expertises.add(new Expertise(5L, "Front side", Date.valueOf(LocalDate.now().plusDays(22)), 700.00, "assets/expertises/peugeot.jpg",claims.get(5) , users.get(2)));
        expertises.add(new Expertise(6L, "Front side", Date.valueOf(LocalDate.now().plusDays(24)), 700.00, "assets/expertises/clio.jpg",claims.get(6) , users.get(2)));

        Long id = users.get(3).getId();

        when(expertiseService.findExpertiseByInsurer(id)).thenReturn(expertises);

        mockMvc.perform(get("/api/v1/expertises/insurer/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expertises.size()));

        verify(expertiseService, times(1)).findExpertiseByInsurer(id);
    }

    @Test
    @WithMockUser
    void saveExpertise() throws Exception {

        List<Expertise> expertises = new ArrayList<>();

        Expertise expertise = new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", null, null);

        when(expertiseService.save(expertise)).thenReturn(expertise);

        mockMvc.perform(post("/api/v1/expertises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expertise)))
                        .andExpect(status().isOk());

        verify(expertiseService, times(1)).save(expertise);
    }

    @Test
    @WithMockUser
    void deleteExpertise() throws Exception {

        List<Expertise> expertises = new ArrayList<>();

        expertises.add(new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", null, null));
        expertises.add(new Expertise(2L, "Wheel damage", Date.valueOf(LocalDate.now().plusDays(13)), 1200.00, "assets/expertises/citroen.jpg",null , null));

        Long id = expertises.get(1).getId();

        doNothing().when(expertiseService).deleteById(id);

        mockMvc.perform(delete("/api/v1/expertises/{id}", id))
                .andExpect(status().isOk());

        verify(expertiseService, times(1)).deleteById(id);
    }

    private String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    }
