package be.helha.assurapp.authentication.controllers;


import be.helha.assurapp.authentication.dto.AuthenticationDTO;
import be.helha.assurapp.authentication.models.User;
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

import java.util.Map;
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path="/api/v1/",consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;

    @PostMapping("register")
    public void register(@RequestBody User user){
        userService.register(user);
    }

    @PostMapping("login")
    public Map<String, String> connexion(@RequestBody AuthenticationDTO authenticationDTO){
        final Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationDTO.username(), authenticationDTO.password()));
        if(authenticate.isAuthenticated()){
            return this.jwtService.generate(authenticationDTO.username());
        }
        return null;
    }
}
