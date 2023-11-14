package be.helha.assurappapi.authentification.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Adresse {
    @Id
    private long id;
    private String street;
    private String state;
    private String city;
    private String postalCode;
    private String country;
}
