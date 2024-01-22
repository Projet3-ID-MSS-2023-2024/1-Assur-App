package be.helha.assurapp.claims.services;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.expertise.enums.ClaimStatus;
import be.helha.assurapp.expertise.models.Claim;
import be.helha.assurapp.expertise.repositories.ClaimRepository;
import be.helha.assurapp.expertise.services.impl.ClaimServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ClaimServiceTest {

    @Mock
    private ClaimRepository claimRepository;

    @InjectMocks
    private ClaimServiceImpl claimService;

    @Test
    void getAllClaimsSuccess() {
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

        when(claimRepository.findAll()).thenReturn(claims);

        List<Claim> result = claimService.findAll();

        assertEquals(claims, result);
        verify(claimRepository, times(1)).findAll();
    }

    @Test
    void getClaimsByClientSuccess() {
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

        when(claimRepository.findClaimByClient(users.get(1).getId())).thenReturn(claims);

        List<Claim> result = claimService.findClaimByClient(users.get(1).getId());

        assertEquals(claims, result);
        verify(claimRepository, times(1)).findClaimByClient(users.get(1).getId());
    }

    @Test
    void getClaimsByExpertSuccess() {
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

        when(claimRepository.findClaimByExpert(users.get(2).getId())).thenReturn(claims);

        List<Claim> result = claimService.findClaimByExpert(users.get(2).getId());

        assertEquals(claims, result);
        verify(claimRepository, times(1)).findClaimByExpert(users.get(2).getId());
    }

    @Test
    void getClaimsByInsurerSuccess() {
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

        when(claimRepository.findClaimByInsurer(users.get(3).getId())).thenReturn(claims);

        List<Claim> result = claimService.findClaimByInsurer(users.get(3).getId());

        assertEquals(claims, result);

        verify(claimRepository, times(1)).findClaimByInsurer(users.get(3).getId());
    }

    @Test
    void getPendingClaimsByInsurerSuccess() {
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

        when(claimRepository.findPendingClaimByInsurer(users.get(3).getId())).thenReturn(claims);

        List<Claim> result = claimService.findPendingClaimByInsurer(users.get(3).getId());

        assertEquals(claims, result);

        verify(claimRepository, times(1)).findPendingClaimByInsurer(users.get(3).getId());
    }

    @Test
    void getOneClaimSuccess() {
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

        when(claimRepository.findById(claims.get(0).getId())).thenReturn(java.util.Optional.ofNullable(claims.get(0)));

        Claim result = claimService.findById(claims.get(0).getId());

        assertEquals(claims.get(0), result);

        verify(claimRepository, times(1)).findById(claims.get(0).getId());
    }

    @Test
    void saveClaimSuccess() {
        Claim claim = new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, null, null);

        when(claimRepository.save(claim)).thenReturn(claim);

        Claim result = claimService.save(claim);

        assertEquals(claim, result);

        verify(claimRepository, times(1)).save(claim);
    }

    @Test
    void updateClaimSuccess() {
        Claim claim = new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, null, null);

        when(claimRepository.save(claim)).thenReturn(claim);

        Claim result = claimService.save(claim);

        assertEquals(claim, result);

        verify(claimRepository, times(1)).save(claim);
    }

    @Test
    void deleteClaimSuccess() {
        Claim claim = new Claim(1L, "Crash clio 4",  Date.valueOf(LocalDate.now()), ClaimStatus.APPROVED, null, null);

        claimService.deleteById(claim.getId());

        verify(claimRepository, times(1)).deleteById(claim.getId());
    }










}
