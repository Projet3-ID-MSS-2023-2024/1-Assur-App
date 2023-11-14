package be.helha.assurappapi.authentification.models;

import be.helha.assurappapi.authentification.interfaces.UserInterface;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class User implements UserInterface{
    @Id
    private Long id;
    @NonNull
    private String firstname;
    @NonNull
    private String lastname;
    @NonNull
    private String email;
    @NonNull
    private String password;
}
