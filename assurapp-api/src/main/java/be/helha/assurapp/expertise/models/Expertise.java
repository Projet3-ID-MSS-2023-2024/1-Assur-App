package be.helha.assurapp.expertise.models;

import be.helha.assurapp.authentication.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "expertises")
public class Expertise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date date;

    private double estimation;

    private String imageFile;

    @OneToOne
    private Claim claim;

    //add expert
    @ManyToOne
    private User expert;
}
