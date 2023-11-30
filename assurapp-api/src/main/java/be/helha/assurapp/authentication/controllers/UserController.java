package be.helha.assurapp.authentication.controllers;


import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.authentication.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(path="/api/v1/",consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;
    @PostMapping("register")
    public void register(@RequestBody User user){
        userService.register(user);
    }
}
