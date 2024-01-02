package be.helha.assurapp.insurance.models;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.expertise.models.Claim;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @NotNull
    private boolean payed;
    @ManyToOne
    private User client;
    @ManyToOne
    private Insurance insurance;
    @OneToMany
    private List<Claim> claims;
    @OneToMany
    private List<Payment> payments;

}
