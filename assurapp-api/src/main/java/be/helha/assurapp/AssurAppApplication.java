package be.helha.assurapp;

import be.helha.assurapp.authentication.repositories.RoleRepository;
import be.helha.assurapp.authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AssurAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(AssurAppApplication.class, args);
    }

}
