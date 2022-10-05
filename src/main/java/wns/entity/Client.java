package wns.entity;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import wns.constants.TypeClients;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "type_client")
    private TypeClients typeClient;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "legal_name")
    private String legalName;
    private int discount;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;
    @Column(name = "from_coming")
    private String fromComing;
    private int limited;
    @Column(columnDefinition = "TEXT")
    private String note;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;
    @Column(name = "black_list")
    private boolean inBlackList;
    @Column(name = "director_of_photography")
    private String directorOfPhotography;
    private String production;
    @Column(name = "number_passport")
    private String numberPassport;
    @Column(name = "issued_by")
    private String issuedBy;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date_issue_passport")
    private LocalDate dateIssuePassport;
    @Column(name = "address_real")
    private String addressReal;
    @Column(name = "address_legal")
    private String addressLegal;
    private String inn;
    private String kpp;
    private String ogrn;
    @Column(name = "full_name_supervisor")
    private String fullNameSupervisor;
    @Column(name = "job_title_supervisor")
    private String jobTitleSupervisor;
    @Column(name = "in_face")
    private String inFace;
    private String based;
    private String rs;
    private String bank;
    private String ks;
    private String bik;

    @Column(name = "date_creating")
    private LocalDate dateCreating;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_clients", joinColumns = @JoinColumn(name = "clients_id"))
    @Column(columnDefinition = "TEXT")
    private Set<String> photos = new HashSet<>();
    private long rented;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && typeClient == client.typeClient && Objects.equals(fullName, client.fullName) && Objects.equals(legalName, client.legalName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeClient, fullName, legalName);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", typeClient=" + typeClient +
                ", fullName='" + fullName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", discount=" + discount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", fromComing='" + fromComing + '\'' +
                ", limit=" + limited +
                ", note='" + note + '\'' +
                ", birthday=" + birthday +
                ", inBlackList=" + inBlackList +
                ", directorOfPhotography='" + directorOfPhotography + '\'' +
                ", production='" + production + '\'' +
                ", numberPassport=" + numberPassport +
                ", issuedBy='" + issuedBy + '\'' +
                ", dateIssuePassport=" + dateIssuePassport +
                ", addressReal='" + addressReal + '\'' +
                ", addressLegal='" + addressLegal + '\'' +
                ", inn=" + inn +
                ", kpp=" + kpp +
                ", ogrn=" + ogrn +
                ", fullNameSupervisor='" + fullNameSupervisor + '\'' +
                ", jobTitleSupervisor='" + jobTitleSupervisor + '\'' +
                ", in_face='" + inFace + '\'' +
                ", based='" + based + '\'' +
                ", rs=" + rs +
                ", bank='" + bank + '\'' +
                ", ks=" + ks +
                ", bik=" + bik +
                ", dateCreating=" + dateCreating +
                ", rented=" + rented +
                '}';
    }
}
