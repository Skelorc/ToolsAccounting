package wns.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "contacts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @Column(name = "number_passport")
    private String numberPassport;
    @Column(name = "issued_by")
    private String issuedBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_issue_passport")
    private LocalDate dateIssuePassport;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_contact_id", nullable = false)
    private RoleContact roleContact;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comments> commentsList;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_contacts", joinColumns = @JoinColumn(name = "contacts_id"))
    @Column(columnDefinition = "TEXT")
    private Set<String> photos = new HashSet<>();


    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", numberPassport='" + numberPassport + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", dateIssuePassport=" + dateIssuePassport +
                ", roleContact=" + roleContact.getRole() +
                ", photos=" + photos.toString() +
                '}';
    }
}
