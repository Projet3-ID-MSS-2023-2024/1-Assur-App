package be.helha.assurappapi.authentification.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Client extends User{
    private String birthdate;
    private String phoneNumber;
    @OneToOne
    private Adresse adresse;
}
