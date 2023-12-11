package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
        //Temporaire, peut etre remplacer une fois le front fonctionnel
        Role userRole = roleRepository.findByLabel(RoleList.SIMPLEUSER);
        roleRepository.save(userRole);
        user.setRole(userRole);

        activationCodeService.sendCode(user);

        this.userRepository.save(user);
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void saveUser(User user){ //duplicate fct to be replaced by CRUD fct
        userRepository.save(user);
    }

}
