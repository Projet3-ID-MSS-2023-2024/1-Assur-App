package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.authentication.utils.RandomStringGenerator;
import lombok.AllArgsConstructor;
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

        activationCodeService.sendCode(user);

        this.userRepository.save(user);
    }

    public void generateChangePasswordCode(User user){
        user.setPwdCode(this.randomStringGenerator.generateRandomString());
        this.userRepository.save(user);
        this.passwordCodeSender.sendCode(user);
    }

    public Boolean changePassword(User user, String newPassword, String oldPassword) throws Exception{
        if(this.passwordEncoder.matches(oldPassword, user.getPassword())){
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
