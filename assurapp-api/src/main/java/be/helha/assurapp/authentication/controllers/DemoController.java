package be.helha.assurapp.authentication.controllers;

import be.helha.assurapp.authentication.models.User;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class DemoController {
    @GetMapping("demo")
    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello from secured endpoint, SimpleUser");
    }
}
