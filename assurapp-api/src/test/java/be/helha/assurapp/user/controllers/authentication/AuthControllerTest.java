package be.helha.assurapp.user.controllers.authentication;
public class AuthControllerTest {}

/*
import be.helha.assurapp.authentication.controllers.UserController;
import be.helha.assurapp.authentication.dto.AuthenticationDTO;
import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.authentication.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthControllerTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserController userController;

    @BeforeEach
    void setUp() {
        Role admin = roleRepository.save(new Role(1L, RoleList.ADMINISTRATOR));
        Role client = roleRepository.save(new Role(2L, RoleList.CLIENT));
        Role expert = roleRepository.save(new Role(3L, RoleList.EXPERT));
        Role insurer = roleRepository.save(new Role(4L, RoleList.INSURER));
        userRepository.save(new User(1L, "Administrator", "Administrator", "admin@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", admin, 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        userRepository.save(new User(2L, "Client", "Client", "client@assurapp.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", client, 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        userRepository.save(new User(4L, "Guardian", "Lite", "insurer@guardianlite.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", insurer, 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        userRepository.save(new User(5L, "Voyager", "Protect", "insurer@voyagerprotect.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", insurer, 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
        userRepository.save(new User(6L, "Expert", "Secure", "Expert@peaksecure.com", "$2a$10$TQETsi01Zxo7r6IG5rVgQONQJeKjOLQRvzjkxAGi7qjU1Zy02VblG", expert, 123456, true, null, "1234 Elm Street Citytown%Stateville 56789%Countryland", "4378.9021-456-78", "0400 000000"));
    }

    @AfterEach
    void atEnd() {
        userRepository.deleteAll();
    }

    @Test
    void loginSuccess(){
        AuthenticationDTO authentication = new AuthenticationDTO("client@assurapp.com", "rootroot");

        Map<String,String> authData = userController.connexion(authentication);

        assertTrue(authData.containsKey("bearer"));
    }

    @Test
    void loginFailedWithErrors(){
        AuthenticationDTO authentication = new AuthenticationDTO("client@assurapp.com", "rtroot");
        try{
            Map<String,String> authData = userController.connexion(authentication);
        }catch(Exception e){
            assertFalse(false);
        }
    }

    @Test
    void verifyAccountSuccess(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(2L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword("rootroot");
        user.setVerified(false);
        user.setActivationCode(123456);
        userRepository.save(user);
        Map<String, String> userData = new HashMap<>();
        userData.put("username","user@test.com");
        userData.put("code", "123456");
        try{
            userController.verify(userData);
        } catch (Exception e){
            fail();
        }
        assertEquals(123456, user.getActivationCode());
    }

}
*/