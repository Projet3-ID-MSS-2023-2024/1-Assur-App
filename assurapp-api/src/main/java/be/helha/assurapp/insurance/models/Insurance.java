package be.helha.assurapp.insurance.models;

import be.helha.assurapp.authentication.models.User;
import be.helha.assurapp.insurance.enums.InsuranceType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "insurances")
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    @Enumerated(EnumType.STRING)
    private InsuranceType type;
    @NotNull
    private double coverageAmount;
    @ManyToOne
    private User insurer;
    @OneToMany
    private List<Term> terms;
}
