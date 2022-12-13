package wns.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role_contact")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "roleContact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts;


}
