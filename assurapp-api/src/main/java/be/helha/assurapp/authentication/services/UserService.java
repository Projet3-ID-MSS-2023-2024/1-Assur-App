package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.expertise.models.Expertise;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        Role userRole = user.getRole();
        user.setRole(roleRepository.findByLabel(userRole.getLabel()));
        return userRepository.save(user);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

}
