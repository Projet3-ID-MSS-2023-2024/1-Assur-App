package be.helha.assurapp.expertise.models;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.expertise.enums.ClaimStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;

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

    @Temporal(TemporalType.DATE)
    private Date date;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private Long expertise;

    // client
    private Long client;
    // insurer
    private Long insurer;

}
