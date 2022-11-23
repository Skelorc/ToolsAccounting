package wns.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role_client")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "roleClient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Client> clients;
}
