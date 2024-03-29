package be.helha.assurapp.authentication.controllers;


import be.helha.assurapp.authentication.dto.AuthenticationDTO;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.repositories.UserRepository;
import be.helha.assurapp.authentication.services.ActivationCodeService;
import be.helha.assurapp.authentication.services.JwtService;
import be.helha.assurapp.authentication.services.UserService;
import be.helha.assurapp.insurance.models.Subscription;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path="/api/v1/")
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;
    private ActivationCodeService activationCodeService;
    private UserRepository userRepository;

    @PostMapping("register")
    public void register(@RequestBody User user){
        userService.register(user);
    }

    @PostMapping("login")
    public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticationDTO){
        final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password()));
        if(authenticate.isAuthenticated() && userService.loadUserByUsername(authenticationDTO.username()).isVerified()){
            return this.jwtService.generate(authenticationDTO.username());
        } else if (!userService.loadUserByUsername(authenticationDTO.username()).isVerified()){
            Map<String, String> errors = new HashMap<>();
            errors.put("Errors", "Not activated account");
            return errors;
        }
        return null;
    }
  
    @GetMapping("users")
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("users/{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping("users")
    public User addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping("usersUpdate")
    public User updateUser(@RequestBody User user ){
        return userService.updateUser(user);
    }

    @DeleteMapping("users/{id}")
    public void deleteById(@PathVariable Long id){
        userService.deleteById(id);
    }

    @GetMapping("users/insurer/{id}")
    public List<User> findUsersByInsurer(@PathVariable Long id){
        return userService.findUsersByInsurer(id);
    }

    @PostMapping("verifyAccount")
    public void verify(@RequestBody Map<String, String> userData) throws Exception{
        if (userData.get("code").isEmpty() || userData.get("username").isEmpty()){
            throw new RuntimeException(); //To be replaced
        }
        if(userService.loadUserByUsername(userData.get("username")).getActivationCode() == Integer.parseInt(userData.get("code"))){
            User user = userService.loadUserByUsername(userData.get("username"));
            user.setVerified(true);
            userRepository.save(user);
        } else {
            throw new Exception();
        }
    }

    @PostMapping("changeActivationCode")
    public void changeActivationCode(@RequestBody Map<String, String> userData){
        User user = userService.loadUserByUsername(userData.get("username"));
        activationCodeService.sendCode(user);
        userRepository.save(user);
    }

    @PostMapping("generatepwdCode")
    public void generatepwdCode(@RequestBody Map<String, String> data){
        userService.generateChangePasswordCode(userService.loadUserByUsername(data.get("username")));
    }

    @PostMapping("changePassword")
    public void changePassword(@RequestBody Map<String, String> data) throws Exception{
        try {
            userService.changePassword(userService.loadUserByUsername(data.get("username")),data.get("newPassword"), data.get("oldPassword"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PostMapping("changePasswordByCode")
    public void changePasswordByCode(@RequestBody Map<String, String> data) throws Exception{
        try {
            userService.changePasswordByCode(userService.loadUserByUsername(data.get("username")), data.get("newPassword"), data.get("code"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @PutMapping("anonymize")
    public void anonymize(@RequestBody Map<String, String> data) throws RuntimeException{
        userService.anonymizeClient(userService.loadUserByUsername(data.get("username")));
    }

    @GetMapping("auth")
    public Authentication getAuth(){
        return userService.getAuthentication();
    }
}
