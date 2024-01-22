package be.helha.assurapp.expertises.services;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.expertise.enums.ClaimStatus;
import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.models.Expertise;
import be.helha.assurapp.expertise.repositories.ExpertiseRepository;
import be.helha.assurapp.expertise.services.impl.ExpertiseServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ExpertiseServiceTest {

    @Mock
    private ExpertiseRepository expertiseRepository;

    @InjectMocks
    private ExpertiseServiceImpl expertiseService;

    @Test
    void getAllExpertises() {
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

        when(expertiseRepository.findAll()).thenReturn(expertises);
        List<Expertise> foundExpertises = expertiseService.findAll();

        assertEquals(expertises, foundExpertises);
        verify(expertiseRepository, times(1)).findAll();
    }

    @Test
    void getExpertiseById() {
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

            when(expertiseRepository.findById(1L)).thenReturn(java.util.Optional.of(expertises.get(0)));

            Expertise foundExpertise = expertiseService.findById(1L);

            assertEquals(expertises.get(0), foundExpertise);

            verify(expertiseRepository, times(1)).findById(1L);
        }

        @Test
        void getExpertiseByInsurer() {
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

                when(expertiseRepository.findExpertiseByInsurer(3L)).thenReturn(expertises);

                List<Expertise> foundExpertises = expertiseService.findExpertiseByInsurer(3L);

                assertEquals(expertises, foundExpertises);

                verify(expertiseRepository, times(1)).findExpertiseByInsurer(3L);
        }

        @Test
        void getExpertiseByExpert() {
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

                when(expertiseRepository.findExpertiseByExpert(3L)).thenReturn(expertises);

                List<Expertise> foundExpertises = expertiseService.findExpertiseByExpert(3L);

                assertEquals(expertises, foundExpertises);

                verify(expertiseRepository, times(1)).findExpertiseByExpert(3L);
        }

        @Test
        void saveExpertise() {
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

            Expertise expertise = new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", claims.get(3), users.get(2));

            when(expertiseRepository.save(expertise)).thenReturn(expertise);

            Expertise savedExpertise = expertiseService.save(expertise);

            assertEquals(expertise, savedExpertise);

            verify(expertiseRepository, times(1)).save(expertise);
        }

        @Test
        void updateExpertise() {
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

            Expertise expertise = new Expertise(1L, "Front side updated", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", claims.get(0), users.get(2));

            when(expertiseRepository.save(expertise)).thenReturn(expertise);

            Expertise updatedExpertise = expertiseService.save(expertise);

            assertEquals(expertise, updatedExpertise);

            verify(expertiseRepository, times(1)).save(expertise);
        }

        @Test
        void deleteExpertise() {
            Expertise expertise = new Expertise(1L, "Front side", Date.valueOf(LocalDate.now()), 700.00, "assets/expertises/clio.jpg", null, null);

            expertiseService.deleteById(expertise.getId());

            verify(expertiseRepository, times(1)).deleteById(expertise.getId());

        }

}
