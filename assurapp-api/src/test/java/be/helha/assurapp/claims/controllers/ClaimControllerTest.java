package be.helha.assurapp.claims.controllers;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.expertise.controllers.ClaimController;
import be.helha.assurapp.expertise.enums.ClaimStatus;
import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.services.IClaimService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;

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
class ClaimControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IClaimService claimService;
    @InjectMocks
    private ClaimController claimController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(claimController).build();
    }

    @Test
    @WithMockUser
    void findAllClaims() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        when(claimService.findAll()).thenReturn(claims);

        mockMvc.perform(get("/api/v1/claims"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(claims.size()));


        verify(claimService, times(1)).findAll();
    }

    @Test
    @WithMockUser
    void findClaimsByClient() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        Long id = users.get(1).getId();

        when(claimService.findClaimByClient(id)).thenReturn(claims);

        mockMvc.perform(get("/api/v1/claims/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(claims.size()));

        verify(claimService, times(1)).findClaimByClient(id);

    }

    @Test
    @WithMockUser
    void findClaimsByExpert() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        Long id = users.get(2).getId();

        when(claimService.findClaimByExpert(id)).thenReturn(claims);

        mockMvc.perform(get("/api/v1/claims/expert/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(claims.size()));

        verify(claimService, times(1)).findClaimByExpert(id);

    }

    @Test
    @WithMockUser
    void findClaimsByInsurer() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        Long id = users.get(3).getId();

        when(claimService.findClaimByInsurer(id)).thenReturn(claims);

        mockMvc.perform(get("/api/v1/claims/insurer/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(claims.size()));

        verify(claimService, times(1)).findClaimByInsurer(id);
    }

    @Test
    @WithMockUser
    void findClaimsByStatus() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        claims.add(new Claim(1L, "Crash clio 4", Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, users.get(1), users.get(2)));
        claims.add(new Claim(2L, "Crash Citroen C3", Date.valueOf(LocalDate.now().plusDays(12)), ClaimStatus.REFUSED, users.get(1), users.get(2)));
        claims.add(new Claim(3L, "Crash Peugeut 206", Date.valueOf(LocalDate.now().plusDays(16)), ClaimStatus.PENDING, users.get(1), null));
        claims.add(new Claim(4L, "Crash Renault", Date.valueOf(LocalDate.now().plusDays(18)), ClaimStatus.ASSIGNED, users.get(1), users.get(2)));
        claims.add(new Claim(5L, "Crash Mercedes", Date.valueOf(LocalDate.now().plusDays(20)), ClaimStatus.PROGRESS, users.get(1), users.get(2)));
        claims.add(new Claim(6L, "Crash Audi", Date.valueOf(LocalDate.now().plusDays(22)), ClaimStatus.CLOSED, users.get(1), users.get(2)));
        claims.add(new Claim(7L, "Crash Skoda", Date.valueOf(LocalDate.now().plusDays(24)), ClaimStatus.CANCELLED, users.get(1), users.get(2)));

        String status = "CLOSED";

        when(claimService.findClaimByStatus(status)).thenReturn(claims);

        mockMvc.perform(get("/api/v1/claims/status/{status}", status))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(claims.size()));

        verify(claimService, times(1)).findClaimByStatus(status);

    }

    @Test
    @WithMockUser
    void findClaimsById() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        Long id = claims.get(0).getId();

        when(claimService.findById(id)).thenReturn(claims.get(0));

        mockMvc.perform(get("/api/v1/claims/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id));

        verify(claimService, times(1)).findById(id);

    }

    @Test
    @WithMockUser
    void update() throws Exception {

        List<Claim> claims = new ArrayList<>();
        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        Long id = claims.get(0).getId();

        Claim claim = claims.get(0);

        claim.setStatus(ClaimStatus.CANCELLED);

        when(claimService.update(claim)).thenReturn(claim);

        mockMvc.perform(put("/api/v1/claims", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(claim)))
                .andExpect(status().isOk());

        verify(claimService, times(1)).update(claim);
    }

    @Test
    @WithMockUser
    void createClaimSucces() throws Exception {

        List<User> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

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

        Claim claim = new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, users.get(1), users.get(2));

        when(claimService.save(claim)).thenReturn(claim);

        mockMvc.perform(post("/api/v1/claims")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(claim)))
                .andExpect(status().isOk());

        verify(claimService, times(1)).save(claim);
    }

    @Test
    @WithMockUser
    void deleteClaimSucces() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/v1/claims/{id}", id))
                .andExpect(status().isOk());

        verify(claimService, times(1)).deleteById(id);
        }

    private String asJsonString(final Object obj) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
