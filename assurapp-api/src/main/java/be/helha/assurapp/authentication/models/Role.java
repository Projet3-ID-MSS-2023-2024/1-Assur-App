package be.helha.assurapp.authentication.models;


import be.helha.assurapp.authentication.enums.RoleList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleList label;

    public Role(String label) {
        this.id = 0L;
        switch (label){
            case "CLIENT":
                this.label = RoleList.CLIENT;
                break;
            case "INSURER":
                this.label = RoleList.INSURER;
                break;
            case "EXPERT":
                this.label = RoleList.EXPERT;
                break;
        }
    }
}
