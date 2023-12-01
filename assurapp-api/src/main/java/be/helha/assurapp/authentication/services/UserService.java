package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.enums.RoleList;
import be.helha.assurapp.authentication.models.Role;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
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


        Role userRole = new Role();
        userRole.setLabel(RoleList.SIMPLEUSER);

        this.userRepository.save(user);
    }





}
