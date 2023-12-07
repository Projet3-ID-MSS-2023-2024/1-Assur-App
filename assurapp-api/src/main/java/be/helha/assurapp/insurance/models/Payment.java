package be.helha.assurapp.insurance.models;

import be.helha.assurapp.insurance.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Date transactionDate;
    private PaymentStatus status; // e.g., Pending, Completed, Failed
    @ManyToOne
    private Subscription subscription;
}
