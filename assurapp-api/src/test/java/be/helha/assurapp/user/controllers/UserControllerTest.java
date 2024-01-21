package be.helha.assurapp.user.controllers;

import be.helha.assurapp.authentication.controllers.UserController;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.authentication.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private RoleRepository roleRepository;

    @InjectMocks
    private UserController userController;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void test_GetAllUsers(){

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1L, "John", "Doe", "john@example.com", "password", new Role(), 1234, true, "pwdCode", "address", "legalId", "phoneNumber"));
        expectedUsers.add(new User(2L, "Jane", "Smith", "jane@example.com", "password", new Role(), 5678, true, "pwdCode", "address", "legalId", "phoneNumber"));
        when(userService.findAll()).thenReturn(expectedUsers);


        List<User> actualUsers = userController.findAll();


        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void test_GetAllUsers_When_DB_isEmpty() {

        List<User> expectedUsers = new ArrayList<>();
        when(userService.findAll()).thenReturn(expectedUsers);


        List<User> actualUsers = userController.findAll();


        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void test_FindUserById() {
        Long id = 1L;
        User expectedUser = new User();
        expectedUser.setId(id);

        UserService userService = mock(UserService.class);
        when(userService.findById(id)).thenReturn(expectedUser);

        UserController userController = new UserController(null, userService, null, null, null);

        User actualUser = userController.findById(id);

        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void test_FindUserById_return_null_if_no_user_with_given_id_is_found() {
        Long id = 100L;
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(null, userService, null, null, null);
        when(userService.findById(id)).thenReturn(null);
        User actualUser = userController.findById(id);
        assertNull(actualUser);
    }

    @Test
    @WithMockUser
    public void test_FindUserById_throw_exception_if_id_is_null() {
        Long id = null;
        UserController userController = mock(UserController.class);
        UserService userService = mock(UserService.class);
        when(userController.findById(id)).thenThrow(new NullPointerException());
        assertThrows(NullPointerException.class, () -> {
            userController.findById(id);
        });
    }

    @Test
    public void test_FindUserById_if_id_is_negative() {
        Long id = -1L;

        UserService userService = mock(UserService.class);
        UserController userController = new UserController(null, userService, null, null, null);

        when(userService.findById(id)).thenReturn(null);

        User actualUser = userController.findById(id);

        assertNull(actualUser);
    }

    @Test
    public void test_addUser() {
        User expectedUser = new User();
        expectedUser.setId(1L);

        UserService userService = mock(UserService.class);
        UserController userController = new UserController(null, userService, null, null, null);

        when(userService.addUser(expectedUser)).thenReturn(expectedUser);

        User actualUser = userController.addUser(expectedUser);

        assertEquals(expectedUser, actualUser);
    }


    @Test
    public void test_updateUser_validInput() {

        User user = new User();
        user.setName("John");
        user.setLastname("Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password123");

        UserService userService = mock(UserService.class);
        when(userService.updateUser(any(User.class))).thenReturn(user);

        UserController userController = new UserController(null, userService, null, null, null);


        User updatedUser = userController.updateUser(user);


        assertNotNull(updatedUser);
        assertEquals("John", updatedUser.getName());
        assertEquals("Doe", updatedUser.getLastname());
        assertEquals("johndoe@example.com", updatedUser.getEmail());
    }

    @Test
    public void test_valid_id_should_return_user_object() {
        // Arrange
        Long id = 1L;
        User expectedUser = new User();
        when(userService.findById(id)).thenReturn(expectedUser);

        // Act
        User actualUser = userController.findById(id);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void test_should_Return_List_Of_Users_For_Given_InsurerId() {
        // Arrange
        Long insurerId = 1L;
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User(1L, "John", "Doe", "john.doe@example.com", "password", new Role(), 1234, true, "pwdCode", "address", "legalId", "phoneNumber"));
        expectedUsers.add(new User(2L, "Jane", "Smith", "jane.smith@example.com", "password", new Role(), 5678, true, "pwdCode", "address", "legalId", "phoneNumber"));
        when(userService.findUsersByInsurer(insurerId)).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userController.findUsersByInsurer(insurerId);

        // Assert
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void test_should_Return_Empty_List_For_No_Users_Associated_With_InsurerId() {
        // Arrange
        Long insurerId = 1L;
        List<User> expectedUsers = new ArrayList<>();
        when(userService.findUsersByInsurer(insurerId)).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userController.findUsersByInsurer(insurerId);

        // Assert
        assertEquals(expectedUsers, actualUsers);
    }
}

