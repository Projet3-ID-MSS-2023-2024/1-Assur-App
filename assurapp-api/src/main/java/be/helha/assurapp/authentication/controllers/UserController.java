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
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(path="/api/v1/")
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

    @PostMapping("usersUpdate")
    public User updateUser(@RequestBody User user ){
        return userService.updateUser(user);
    }

    @DeleteMapping("users/{id}")
    public void deleteById(@PathVariable Long id){
        userService.deleteById(id);
    }


}
