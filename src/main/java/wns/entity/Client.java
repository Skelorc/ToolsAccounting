package wns.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import wns.constants.Roles;
import wns.constants.TypeClients;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
@NoArgsConstructor
@Getter
@Setter
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
    private String phone_number;
    private String email;
    private String from_coming;
    private long limit;
    @Column(columnDefinition = "TEXT")
    private String note;
    @Column(name = "address_real")
    private String addressReal;
    @Column(name = "address_legal")
    private String addressLegal;
    private long inn;
    private long kpp;
    private long ogrn;
    @Column(name = "full_name_supervisor")
    private String fullNameSupervisor;
    @Column(name = "job_title_supervisor")
    private String jobTitleSupervisor;
    private String in_face;
    private String based;
    private long rs;
    private String bank;
    private long ks;
    private long bik;
    private LocalDate birthday;
    @Column(name = "black_list")
    private boolean inBlackList;
    @Column(name = "director_of_photography")
    private String directorOfPhotography;
    private String production;
    @Column(name = "number_passport")
    private int numberPassport;
    private String issued_by;
    @Column(name = "date_issue_passport")
    private LocalDate dateIssuePassport;
    @Column(name = "date_creating")
    private LocalDate dateCreating;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "photos_clients", joinColumns = @JoinColumn(name = "clients_id"))
    private Set<String> photos = new HashSet<>();
    private long rented;


    public Client createLegalClient(TypeClients typeClient, String fullName,
                                    String legalName, int discount,
                                    String phone_number, String email, String from_coming,
                                    long limit, String note, String addressReal,
                                    String addressLegal, long inn, boolean inBlackList, long kpp, long ogrn,
                                    String fullNameSupervisor, String jobTitleSupervisor, String in_face, String based,
                                    long rs, String bank, long ks, long bik, LocalDate dateCreating, List<Project> projects,
                                    Set<String> photos, long rented) {
        Client client = new Client();
        client.typeClient = typeClient;
        client.fullName = fullName;
        client.legalName = legalName;
        client.discount = discount;
        client.phone_number = phone_number;
        client.email = email;
        client.from_coming = from_coming;
        client.limit = limit;
        client.note = note;
        client.addressReal = addressReal;
        client.addressLegal = addressLegal;
        client.inn = inn;
        client.inBlackList = inBlackList;
        client.kpp = kpp;
        client.ogrn = ogrn;
        client.fullNameSupervisor = fullNameSupervisor;
        client.jobTitleSupervisor = jobTitleSupervisor;
        client.in_face = in_face;
        client.based = based;
        client.rs = rs;
        client.bank = bank;
        client.ks = ks;
        client.bik = bik;
        client.dateCreating = dateCreating;
        client.projects = projects;
        client.photos = photos;
        client.rented = rented;
        return client;
    }

    public Client createIndividualClient(TypeClients typeClient, String fullName, int discount, String phone_number,
                                         String email, String from_coming, long limit, String note, String addressReal,
                                         LocalDate birthday, boolean inBlackList, String directorOfPhotography, String production,
                                         int numberPassport, String issued_by, LocalDate dateIssuePassport, LocalDate dateCreating,
                                         List<Project> projects, Set<String> photos, long rented) {
        Client client = new Client();
        client.typeClient = typeClient;
        client.fullName = fullName;
        client.discount = discount;
        client.phone_number = phone_number;
        client.email = email;
        client.from_coming = from_coming;
        client.limit = limit;
        client.note = note;
        client.addressReal = addressReal;
        client.birthday = birthday;
        client.inBlackList = inBlackList;
        client.directorOfPhotography = directorOfPhotography;
        client.production = production;
        client.numberPassport = numberPassport;
        client.issued_by = issued_by;
        client.dateIssuePassport = dateIssuePassport;
        client.dateCreating = dateCreating;
        client.projects = projects;
        client.photos = photos;
        client.rented = rented;
        return client;
    }
}
