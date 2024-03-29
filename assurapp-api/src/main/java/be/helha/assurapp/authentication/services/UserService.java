package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.authentication.utils.RandomStringGenerator;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private ActivationCodeService activationCodeService;
    private RandomStringGenerator randomStringGenerator;
    private PasswordCodeSender passwordCodeSender;

    /**
     * Register a user and attribute the associate role, if no user first user will be admin
     * @param user
     * @throws RuntimeException
     */
    public void register(User user) throws RuntimeException{

        String cypherPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(cypherPassword);

        if(!user.getEmail().contains("@") && !user.getEmail().contains(".")){ //to be replaced by Email Validator
            throw new RuntimeException("Invalid format");
        }

        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()){
            throw new RuntimeException("Used email");
        }

        Role userRole = user.getRole();
        if(userRole.getLabel() != RoleList.ADMINISTRATOR){
            user.setRole(roleRepository.findByLabel(userRole.getLabel()));
        }

        if(userRepository.findAll().isEmpty()) {
            user.setRole(roleRepository.findByLabel(RoleList.ADMINISTRATOR));
        }

        activationCodeService.sendCode(user);

        this.userRepository.save(user);
    }

    public void generateChangePasswordCode(User user){
        user.setPwdCode(this.randomStringGenerator.generateRandomString());
        this.userRepository.save(user);
        this.passwordCodeSender.sendCode(user);
    }

    public Boolean changePassword(User user, String newPassword, String oldPassword) throws Exception{
        String authEmail = getAuthentication().getName();
        if(this.passwordEncoder.matches(oldPassword, user.getPassword()) && user.getEmail().equals(authEmail)){
            user.setPassword(this.passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        throw new RuntimeException("Incorrect Data");
    }

    public Boolean changePasswordByCode(User user, String newPassword, String code) throws Exception{
        if(user.getPwdCode().equals(code)){
            user.setPassword(this.passwordEncoder.encode(newPassword));
            user.setPwdCode(null);
            userRepository.save(user);
            return true;
        }
        throw new RuntimeException("Incorrect Data");
    }

    public Boolean anonymizeClient(User user) throws RuntimeException{
        String authEmail = getAuthentication().getName();
        if(userRepository.findInsuranceByUser(user.getId()).isEmpty() && user.getRole().getLabel() == RoleList.CLIENT && user.getEmail().equals(authEmail)){
            user.setName("XXXXXX");
            user.setLastname("XXXXXX");
            user.setAddress("XXXXX%XXXXXX%XXXXXX");
            user.setEmail("deletedUser"+user.getId()+"@anonyme.assurapp.com");
            user.setLegalId("XXXXXXXXXX");
            user.setPassword(passwordEncoder.encode("rootroot"));
            user.setPhoneNumber("XXXXXXXXXXXX");
            userRepository.save(user);
            return true;
        }else if(user.getRole().getLabel() != RoleList.CLIENT){
            throw new RuntimeException("Error: unauthorized request");
        } else {
            throw new RuntimeException("Error: you still have subscription");
        }


    }

    public Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }


    public User updateUser(User user) {
        String pwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        Role userRole = user.getRole();
        user.setRole(roleRepository.findByLabel(userRole.getLabel()));
        return userRepository.save(user);
    }

    public User addUser(User user) {

        register(user);
        user.setVerified(true);
        return userRepository.save(user);
    }


    public List<User> findUsersByInsurer(Long id) {
        return userRepository.findUsersByInsurer(id);
    }



}
