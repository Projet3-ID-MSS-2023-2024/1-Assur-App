package be.helha.assurapp.authentication.controllers;


import be.helha.assurapp.authentication.dto.AuthenticationDTO;
import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.services.ActivationCodeService;
import be.helha.assurapp.authentication.services.JwtService;
import be.helha.assurapp.authentication.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path="/api/v1/",consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;
    private ActivationCodeService activationCodeService;

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

    @PostMapping("verifyAccount")
    public void verify(@RequestBody Map<String, String> userData){
        if (userData.get("code").isEmpty() || userData.get("username").isEmpty()){
            throw new RuntimeException(); //To be replaced
        }
        if(userService.loadUserByUsername(userData.get("username")).getActivationCode() == Integer.parseInt(userData.get("code"))){
            userService.loadUserByUsername(userData.get("username")).setVerified(true);
            userService.saveUser(userService.loadUserByUsername(userData.get("username")));//To be replaced by CRUD fct
        }
    }

    @PostMapping("changeActivationCode")
    public void changeActivationCode(@RequestBody Map<String, String> userData){
        activationCodeService.sendCode(userService.loadUserByUsername(userData.get("username")));
        userService.saveUser(userService.loadUserByUsername(userData.get("username")));
    }

}
