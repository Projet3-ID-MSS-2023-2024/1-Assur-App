package be.helha.assurapp.insurance.models;

import be.helha.assurapp.authentication.User;
import be.helha.assurapp.insurance.enums.ClaimStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String description;
    @NotNull
    private Date date;
    private double amount;
    @NotNull
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;
    @OneToOne
    private User client;
    @OneToOne
    private User expert;
    @OneToOne
    private User insurer;

}
