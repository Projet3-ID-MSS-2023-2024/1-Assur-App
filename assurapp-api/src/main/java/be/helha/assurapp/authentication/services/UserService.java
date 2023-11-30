package be.helha.assurapp.authentication.services;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    public void register(User user){
        String cypherPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(cypherPassword);
        this.userRepository.save(user);
    }


}
