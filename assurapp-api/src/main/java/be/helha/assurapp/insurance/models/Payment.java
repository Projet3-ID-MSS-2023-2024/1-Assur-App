package be.helha.assurapp.insurance.models;

import be.helha.assurapp.insurance.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Double amount;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    @ManyToOne
    private Subscription subscription;
}
