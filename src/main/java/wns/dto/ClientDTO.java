package wns.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import wns.constants.TypeClients;
import wns.entity.Client;
import wns.entity.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Data
public class ClientDTO {
    private long id;
    private TypeClients typeClient;
    private String fullName;
    private String legalName;
    private int discount;
    private String phoneNumber;
    private String email;
    private String fromComing;
    private int limited;
    private String note;
    private LocalDate birthday;
    private boolean inBlackList;
    private String directorOfPhotography;
    private String production;
    private String numberPassport;
    private String issuedBy;
    private LocalDate dateIssuePassport;
    private String addressReal;
    private String addressLegal;
    private String inn;
    private String kpp;
    private String ogrn;
    private String fullNameSupervisor;
    private String jobTitleSupervisor;
    private String inFace;
    private String based;
    private String rs;
    private String bank;
    private String ks;
    private String bik;
    private LocalDate dateCreating;
    private List<Project> projects;
    private Set<String> photos;
    private long rented;

    public Client createClient(ClientDTO dto)
    {
        if(dto.getTypeClient().equals(TypeClients.INDIVIDUAL))
        {
            return createIndividualFromDTO(dto);
        }
        else
            return createLegalFromDTO(dto);
    }

    public ClientDTO (Client client)
    {
        photos = new HashSet<>();
        projects = new ArrayList<>();
        this.setId(client.getId());
        this.setTypeClient(client.getTypeClient());
        this.setFullName(client.getFullName());
        this.setLegalName(client.getLegalName());
        this.setDiscount(client.getDiscount());
        this.setPhoneNumber(client.getPhoneNumber());
        this.setEmail(client.getEmail());
        this.setFromComing(client.getFromComing());
        this.setLimited(client.getLimited());
        this.setNote(client.getNote());
        this.setBirthday(client.getBirthday());
        this.setInBlackList(client.isInBlackList());
        this.setDirectorOfPhotography(client.getDirectorOfPhotography());
        this.setProduction(client.getProduction());
        this.setNumberPassport(client.getNumberPassport());
        this.setIssuedBy(client.getIssuedBy());
        this.setDateIssuePassport(client.getDateIssuePassport());
        this.setAddressReal(client.getAddressReal());
        this.setAddressLegal(client.getAddressLegal());
        this.setInn(client.getInn());
        this.setKpp(client.getKpp());
        this.setOgrn(client.getOgrn());
        this.setFullNameSupervisor(client.getFullNameSupervisor());
        this.setJobTitleSupervisor(client.getJobTitleSupervisor());
        this.setInFace(client.getInFace());
        this.setBased(client.getBased());
        this.setRs(client.getRs());
        this.setBank(client.getBank());
        this.setKs(client.getKs());
        this.setBik(client.getBik());
        this.setDateCreating(client.getDateCreating());
        this.setProjects(client.getProjects());
        this.setPhotos(client.getPhotos());
        this.setRented(client.getRented());
    }

    private Client createIndividualFromDTO(ClientDTO dto) {
        return Client.builder()
                .typeClient(dto.getTypeClient())
                .fullName(dto.getFullName())
                .discount(dto.getDiscount())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .fromComing(dto.getFromComing())
                .limited(dto.getLimited())
                .note(dto.getNote())
                .birthday(dto.getBirthday())
                .inBlackList(dto.isInBlackList())
                .directorOfPhotography(dto.getDirectorOfPhotography())
                .production(dto.getProduction())
                .numberPassport(dto.getNumberPassport())
                .issuedBy(dto.getIssuedBy())
                .dateIssuePassport(dto.getDateIssuePassport())
                .addressReal(dto.getAddressReal())
                .dateCreating(dto.getDateCreating())
                .projects(dto.getProjects())
                .photos(dto.getPhotos())
                .rented(dto.getRented())
                .build();
    }

    private Client createLegalFromDTO(ClientDTO dto) {
        return Client.builder()
                .typeClient(dto.getTypeClient())
                .fullName(dto.getFullName())
                .legalName(dto.getLegalName())
                .discount(dto.getDiscount())
                .phoneNumber(dto.getPhoneNumber())
                .email(dto.getEmail())
                .fromComing(dto.getFromComing())
                .limited(dto.getLimited())
                .note(dto.getNote())
                .inBlackList(dto.isInBlackList())
                .addressReal(dto.getAddressReal())
                .addressLegal(dto.getAddressLegal())
                .inn(dto.getInn())
                .kpp(dto.getKpp())
                .ogrn(dto.getOgrn())
                .fullNameSupervisor(dto.getFullNameSupervisor())
                .jobTitleSupervisor(dto.getJobTitleSupervisor())
                .inFace(dto.getInFace())
                .based(dto.getBased())
                .rs(dto.getRs())
                .bank(dto.getBank())
                .ks(dto.getKs())
                .bik(dto.getBik())
                .dateCreating(dto.getDateCreating())
                .projects(dto.getProjects())
                .photos(dto.getPhotos())
                .rented(dto.getRented())
                .build();
    }
}
