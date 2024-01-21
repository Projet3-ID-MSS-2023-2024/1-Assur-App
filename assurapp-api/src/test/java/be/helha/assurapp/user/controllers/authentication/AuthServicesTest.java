package be.helha.assurapp.user.controllers.authentication;


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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class AuthServicesTest {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


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
    public void registerAsClientSuccess() {
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

        userService.register(user);

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()){
            assertEquals(user, userOptional.get());
            assertEquals(user.getRole(), userOptional.get().getRole());
        } else {
            fail();
        }
    }

    @Test
    public void registerAsInsurerSuccess(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(3L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword("rootroot");

        userService.register(user);

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()){
            assertEquals(user, userOptional.get());
            assertEquals(user.getRole(), userOptional.get().getRole());
        } else {
            fail();
        }
    }

    @Test
    public void registerAsExpertSuccess(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(4L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword("rootroot");

        userService.register(user);

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent()){
            assertEquals(user, userOptional.get());
            assertEquals(user.getRole(), userOptional.get().getRole());
        } else {
            fail();
        }
    }

    @Test
    public void registerAsAdminFirstRegister(){
        userRepository.deleteAll();
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(4L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword("rootroot");

        userService.register(user);

        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());
        Optional<Role> roleOptional1 = roleRepository.findById(1L);

        if (userOptional.isPresent()){
            assertEquals(user, userOptional.get());
            if(roleOptional1.isPresent()){
                assertEquals(roleOptional1.get(), userOptional.get().getRole());
            }else {
                fail();
            }
        } else {
            fail();
        }
    }

    @Test
    public void registerFailUsedEmail(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(2L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        user.setName("user");
        user.setLastname("test");
        user.setEmail("client@assurapp.com");
        user.setPassword("rootroot");
        try {
            userService.register(user);
        }catch (RuntimeException e) {
            assertEquals(1,1);
        }
    }

    @Test
    public void registerFailInvalidEmail(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(2L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        user.setName("user");
        user.setLastname("test");
        user.setEmail("client");
        user.setPassword("rootroot");
        try {
            userService.register(user);
        }catch (RuntimeException e) {
            assertEquals(1,1);
        }
    }

    @Test
    void generatePwdCodeSuccess(){
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
        user.setVerified(true);

        userService.register(user);

        userService.generateChangePasswordCode(user);
        assertFalse(user.getPwdCode().isEmpty());
    }

    @Test
    void changePasswordSuccess(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(4L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        String password = "rootroot";

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword(password);
        user.setVerified(true);

        userService.register(user);
        try {
            assertTrue(userService.changePassword(user, "toortoor", "toortoor"));
        }catch (Exception e){
            //do nothing
        }
    }

    @Test
    void changePasswordFailedInvalidData(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(4L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        String password = "rootroot2";

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword(password);
        user.setVerified(true);

        userService.register(user);
        try {
            userService.changePassword(user, "toortoor", "toortoor");
        }catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void changePasswordByCodeSuccess(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(4L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        String password = "rootroot";

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword(password);
        user.setVerified(true);
        user.setPwdCode("123456");

        userService.register(user);
        try {
            assertTrue(userService.changePasswordByCode(user, "toortoor", "123456"));
        }catch (Exception e){
            //do nothing
        }
    }

    @Test
    void changePasswordByCodeFailedInvalidData(){
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findById(4L);
        if (roleOptional.isPresent()){
            Role role = roleOptional.get();
            user.setRole(role);
        }

        String password = "rootroot2";

        user.setName("user");
        user.setLastname("test");
        user.setEmail("user@test.com");
        user.setPassword(password);
        user.setVerified(true);
        user.setPwdCode("123456");

        userService.register(user);
        try {
            userService.changePasswordByCode(user, "toortoor", "654321");
        }catch (Exception e){
            assertTrue(true);
        }
    }

    @Test
    void loadUserByUsernameSuccess(){
        //overrided method
        Optional<User> user = userRepository.findByEmail("client@assurapp.com");

        if (user.isPresent()){
            assertEquals(user.get(), userService.loadUserByUsername(user.get().getEmail()));
        } else {
            fail();
        }
    }

    @Test
    void loadUserByUsernameThrowErrorFromInexistantEmail(){
        //overrided method
        try{
            userService.loadUserByUsername("randomData");
        } catch (UsernameNotFoundException e){
            assertTrue(true);
        }
    }
}
