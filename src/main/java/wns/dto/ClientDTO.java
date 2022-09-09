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


    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", typeClient=" + typeClient +
                ", fullName='" + fullName + '\'' +
                ", legalName='" + legalName + '\'' +
                ", discount=" + discount +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", fromComing='" + fromComing + '\'' +
                ", limited=" + limited +
                ", note='" + note + '\'' +
                ", birthday=" + birthday +
                ", inBlackList=" + inBlackList +
                ", directorOfPhotography='" + directorOfPhotography + '\'' +
                ", production='" + production + '\'' +
                ", numberPassport='" + numberPassport + '\'' +
                ", issuedBy='" + issuedBy + '\'' +
                ", dateIssuePassport=" + dateIssuePassport +
                ", addressReal='" + addressReal + '\'' +
                ", addressLegal='" + addressLegal + '\'' +
                ", inn='" + inn + '\'' +
                ", kpp='" + kpp + '\'' +
                ", ogrn='" + ogrn + '\'' +
                ", fullNameSupervisor='" + fullNameSupervisor + '\'' +
                ", jobTitleSupervisor='" + jobTitleSupervisor + '\'' +
                ", inFace='" + inFace + '\'' +
                ", based='" + based + '\'' +
                ", rs='" + rs + '\'' +
                ", bank='" + bank + '\'' +
                ", ks='" + ks + '\'' +
                ", bik='" + bik + '\'' +
                ", dateCreating=" + dateCreating +
                ", rented=" + rented +
                '}';
    }
}
